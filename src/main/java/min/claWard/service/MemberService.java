package min.claWard.service;


import min.claWard.domain.Member;
import min.claWard.web.dto.MemberSignupRequest;
import min.claWard.web.dto.MemberLoginRequest;

public interface MemberService {
    Member register(MemberSignupRequest request);
    Member login(MemberLoginRequest request);
}

