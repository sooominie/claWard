package min.claWard.web.controller;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.service.MemberService;
import min.claWard.web.dto.MemberLoginRequest;
import min.claWard.web.dto.MemberSignupRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public Member signup(@RequestBody MemberSignupRequest request) {
        return memberService.register(request);
    }

    @PostMapping("/login")
    public Member login(@RequestBody MemberLoginRequest request) {
        return memberService.login(request);
    }

    @GetMapping("/me")
    public Member getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return (Member) userDetails;
    }


}
