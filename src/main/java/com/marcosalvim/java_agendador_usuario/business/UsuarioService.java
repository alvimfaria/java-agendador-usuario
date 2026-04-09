package com.marcosalvim.java_agendador_usuario.business;

import com.marcosalvim.java_agendador_usuario.business.converter.UsuarioConverter;
import com.marcosalvim.java_agendador_usuario.business.dto.UsuarioDTO;
import com.marcosalvim.java_agendador_usuario.infrastructure.entity.Usuario;
import com.marcosalvim.java_agendador_usuario.infrastructure.exceptions.ConflictException;
import com.marcosalvim.java_agendador_usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.marcosalvim.java_agendador_usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {

        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Email não encontrado " + email)
                            )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }


    public void deletaUsuarioPorEmail(String email) {

        usuarioRepository.deleteByEmail(email);
    }


}
