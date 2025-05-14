package min.claWard.service.impl;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.repository.MemberRepository;
import min.claWard.security.JwtTokenProvider;
import min.claWard.service.MemberService;
import min.claWard.web.dto.MemberLoginRequest;
import min.claWard.web.dto.MemberSignupRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Member register(MemberSignupRequest request) {
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return memberRepository.save(member);
    }

    @Override
    public String login(MemberLoginRequest request) {
        // 인증 시도
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        // 인증 성공 시 토큰 발급
        return jwtTokenProvider.createToken(
                request.getEmail(),
                authentication.getAuthorities().iterator().next().getAuthority()
        );


    }


}
