package loja.loja.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loja.loja.entity.Usuario;
import loja.loja.repository.UsuarioRepository;
import loja.loja.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        System.out.println("🔥 HEADER: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.replace("Bearer ", "");

            try {
                String email = tokenService.validarToken(token);

                System.out.println("🔥 EMAIL DO TOKEN: " + email);

                Usuario usuario = usuarioRepository.findByEmail(email);

                if (usuario != null) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    usuario.getEmail(), // 🔥 IMPORTANTE
                                    null,
                                    new ArrayList<>()
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);

                    System.out.println("🔥 USUARIO AUTENTICADO");
                }

            } catch (Exception e) {
                System.out.println("🔥 TOKEN INVÁLIDO");
            }
        }

        filterChain.doFilter(request, response);
    }
}