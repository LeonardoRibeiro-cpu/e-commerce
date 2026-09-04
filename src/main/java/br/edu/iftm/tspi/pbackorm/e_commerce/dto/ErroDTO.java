package br.edu.iftm.tspi.pbackorm.e_commerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ErroDTO {
    private String mensagem;

    private List<String> erros;

    private LocalDateTime dataHora;

}
