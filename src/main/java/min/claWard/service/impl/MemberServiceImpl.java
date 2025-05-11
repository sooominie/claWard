package min.claWard.service.impl;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.repository.MemberRepository;
import min.claWard.service.MemberService;
import min.claWard.web.dto.MemberLoginRequest;
import min.claWard.web.dto.MemberSignupRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public Member register(MemberSignupRequest request) {
        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
        return memberRepository.save(member);
    }

    @Override
    public Member login(MemberLoginRequest request) {
        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 세션에 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 로그인된 사용자 정보 리턴
        return (Member) authentication.getPrincipal();
    }
}
