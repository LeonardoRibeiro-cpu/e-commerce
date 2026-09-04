package br.edu.iftm.tspi.pbackorm.e_commerce.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PedidoDTO {
  
    private Integer id;
    
    private LocalDateTime dataPedido;

    private String idCliente;

    private List<DetalhePedidoDTO> detalhesPedido;
}
