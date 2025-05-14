package min.claWard.web.controller;

import lombok.RequiredArgsConstructor;
import min.claWard.domain.Member;
import min.claWard.security.CustomUserDetails;
import min.claWard.service.MemberService;
import min.claWard.web.dto.MemberLoginRequest;
import min.claWard.web.dto.MemberSignupRequest;
import min.claWard.web.dto.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberSignupRequest request) {
        return ResponseEntity.ok(memberService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequest request) {
        String jwtToken = memberService.login(request); // 토큰 반환
        return ResponseEntity.ok(jwtToken);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("[ME] userDetails = " + userDetails);
        return ResponseEntity.ok(userDetails.getUsername());
    }



}
