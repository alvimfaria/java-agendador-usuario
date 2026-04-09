package com.marcosalvim.java_agendador_usuario.business;

import com.marcosalvim.java_agendador_usuario.business.converter.UsuarioConverter;
import com.marcosalvim.java_agendador_usuario.business.dto.UsuarioDTO;
import com.marcosalvim.java_agendador_usuario.infrastructure.entity.Usuario;
import com.marcosalvim.java_agendador_usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(
                usuarioRepository.save(usuario)
        );
    }

}
