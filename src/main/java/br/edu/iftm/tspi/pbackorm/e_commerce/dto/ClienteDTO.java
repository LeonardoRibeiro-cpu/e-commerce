package br.edu.iftm.tspi.pbackorm.e_commerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {
    private String idCliente;

    private String nome;

    private String endereco;

    private String cidade;

    private String cep;

    private String pais;

    private String telefone;

    private String fax;

}
