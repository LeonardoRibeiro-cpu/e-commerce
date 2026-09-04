package br.edu.iftm.tspi.pbackorm.e_commerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class DetalhePedidoDTO {
  
    private Integer idPedido;
 
    private Integer idProduto;
    
    private Double precoVenda;

    private Short quantidade;

    private Double desconto;
}
