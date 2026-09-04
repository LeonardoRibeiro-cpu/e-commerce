package br.edu.iftm.tspi.pbackorm.e_commerce.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor 
public class DetalhePedidoID{

     @Column(name = "PedidoID")
     private Integer pedidoId;

     @Column(name = "produtoid")
     private Integer produtoId;
}