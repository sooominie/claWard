package min.claWard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import min.claWard.service.auth.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 🔒 회원가입/로그인은 필터 제외
        if (uri.startsWith("/api/members/signup") || uri.startsWith("/api/members/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken(request);
        String email = null;

        // "Bearer " 접두어 제거
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
            email = jwtTokenProvider.getEmail(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("[JWT FILTER] 저장할 userDetails 타입: " + userDetails.getClass().getName());
            System.out.println("[JWT FILTER] SecurityContext에 저장한 인증 객체: " +
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().getName());




        }

        filterChain.doFilter(request, response);


        System.out.println("[JWT FILTER] 요청 URI: " + uri);
        System.out.println("[JWT FILTER] 추출한 토큰: " + token);
        System.out.println("[JWT FILTER] 이메일: " + email);

    }
}
