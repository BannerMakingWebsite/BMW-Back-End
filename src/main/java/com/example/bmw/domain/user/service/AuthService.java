package com.example.bmw.domain.user.service;

import com.example.bmw.domain.user.controller.dto.UserDto;
import com.example.bmw.domain.user.controller.dto.request.LoginRequest;
import com.example.bmw.domain.user.controller.dto.request.PasswordRequest;
import com.example.bmw.domain.user.controller.dto.request.SignupRequest;
import com.example.bmw.domain.user.controller.dto.response.TokenResponse;
import com.example.bmw.domain.user.controller.dto.response.UserResponse;
import com.example.bmw.domain.user.entity.Authority;
import com.example.bmw.domain.user.entity.LoginType;
import com.example.bmw.domain.user.entity.User;
import com.example.bmw.domain.user.repository.UserRepository;
import com.example.bmw.global.error.ErrorCode;
import com.example.bmw.global.error.exception.CustomException;
import com.example.bmw.global.jwt.TokenProvider;
import com.example.bmw.global.redis.RedisDao;
import com.example.bmw.global.util.MailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final JavaMailSender mailSender;
    private final RedisDao redisDao;

    @Transactional
    public void send(String email){
        String authKey = getAuthCode();

        try {
            MailUtils sendMail = new MailUtils(mailSender);
            sendMail.setSubject("이메일 인증");
            sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                    .append(authKey)
                    .toString());
            sendMail.setFrom("whee007@naver.com", "관리자");
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(userRepository.existsByEmail(email)){
            User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
            user.setAuthKey(authKey);
            userRepository.save(user);
        }
        else{
            userRepository.save(User.builder()
                    .email(email)
                    .authKey(authKey)
                    .build());
        }
    }

    @Transactional
    public void verify(String email, String authKey){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        codeVerify(user.getAuthKey(), authKey);

        user.verify();
        userRepository.save(user);
    }

    @Transactional
    public void passwordVerify(String email, String authKey){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        codeVerify(user.getAuthKey(), authKey);

        user.setPasswordVerify(true);
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(PasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        if(!user.isPasswordVerify()){
            throw new CustomException(ErrorCode.UNAUTHORIZED_EMAIL);
        }
        checkPassword(request.getNewPassword());
        user.setPassword(request.getNewPassword());
        user.passwordEncode(passwordEncoder);
        user.setPasswordVerify(false);
        userRepository.save(user);
    }

    @Transactional
    public UserResponse signup(SignupRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        if(!user.isVerify()){
            throw new CustomException(ErrorCode.UNAUTHORIZED_EMAIL);
        }

        checkPassword(request.getPassword());

        user.signup(request.getPassword(), request.getName());
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getId())
                .goods(user.getGoods())
                .bookmarks(user.getBookmarks())
                .comments(user.getComments())
                .posts(user.getPosts())
                .email(user.getEmail())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .authority(user.getAuthority())
                .loginType(user.getLoginType())
                .build();
    }

    @Transactional
    public TokenResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        String accessToken = tokenProvider.createAccessToken(user.getEmail(), user.getAuthority());
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail());

        userRepository.save(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse google(UserDto userDto){
        if(userRepository.existsByEmail(userDto.getEmail())){
            User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));
            user.profileUpdate(userDto.getName(), userDto.getPicture());
        }
        else{
            userRepository.save(User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .imageUrl(userDto.getPicture())
                    .authority(Authority.USER)
                    .loginType(LoginType.GOOGLE)
                    .build());
        }
        String accessToken = tokenProvider.createAccessToken(userDto.getEmail(), Authority.USER);
        String refreshToken = tokenProvider.createRefreshToken(userDto.getEmail());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public TokenResponse issueAccessToken(HttpServletRequest request){
        String accessToken = tokenProvider.resolveAccessToken(request);
        String refreshToken = tokenProvider.resolveRefreshToken(request);

        if(!tokenProvider.validateAccessToken(accessToken)) {
            log.info("access 토큰 만료됨");
            if(tokenProvider.validateRefreshToken(refreshToken)) {
                log.info("refresh Token 은 유효합니다.");

                String email = tokenProvider.getUserEmail(refreshToken);
                String rtkRedis = redisDao.getValues(email);

                if(Objects.isNull(rtkRedis))
                    throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                accessToken = tokenProvider.createAccessToken(email, Authority.USER);
            }
            else {
                log.info("Refresh Token 이 유효하지 않습니다.");
                throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
            }
        }
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String getAuthCode(){
        Random random  = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6){
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

    private void codeVerify(String authKey, String requestKey){
        if(!Objects.equals(authKey, requestKey)){
            throw new CustomException(ErrorCode.CODE_NOT_MATCH);
        }
    }

    private void checkPassword(String pwd){

        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher1 = passPattern1.matcher(pwd);

        if(!passMatcher1.find()){
            throw new CustomException(ErrorCode.PASSWORD_SHORT);
        }

        // 반복된 문자 확인
        Pattern passPattern2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher passMatcher2 = passPattern2.matcher(pwd);

        if(passMatcher2.find()){
            throw new CustomException(ErrorCode.SAME_CHAR_PASSWORD);
        }

        // 특수문자 확인
        Pattern passPattern3 = Pattern.compile("\\W");
        Pattern passPattern4 = Pattern.compile("[!@#$%^*+=-]");

        for(int i = 0; i < pwd.length(); i++){
            String s = String.valueOf(pwd.charAt(i));
            Matcher passMatcher3 = passPattern3.matcher(s);

            if(passMatcher3.find()){
                Matcher passMatcher4 = passPattern4.matcher(s);
                if(!passMatcher4.find()){
                    throw new CustomException(ErrorCode.OUT_OF_RANGE_SPECIAL_CHAR);
                }
            }
        }

        //연속된 문자 확인
        int ascSeqCharCnt = 0; // 오름차순 연속 문자 카운트
        int descSeqCharCnt = 0; // 내림차순 연속 문자 카운트

        char char_0;
        char char_1;
        char char_2;

        int diff_0_1;
        int diff_1_2;

        for(int i = 0; i < pwd.length()-2; i++){
            char_0 = pwd.charAt(i);
            char_1 = pwd.charAt(i+1);
            char_2 = pwd.charAt(i+2);

            diff_0_1 = char_0 - char_1;
            diff_1_2 = char_1 - char_2;

            if(diff_0_1 == 1 && diff_1_2 == 1){
                ascSeqCharCnt += 1;
            }

            if(diff_0_1 == -1 && diff_1_2 == -1){
                descSeqCharCnt -= 1;
            }
        }

        if(ascSeqCharCnt > 1 || descSeqCharCnt > 1){
            throw new CustomException(ErrorCode.CONTINUOUS_PASSWORD);
        }
    }
}
