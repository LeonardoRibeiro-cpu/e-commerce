package br.edu.iftm.tspi.pbackorm.e_commerce.dto;

public interface ProdutosPorPedidoDTO {
      Integer getNumeroPedido();
      Integer getQuantidade();
      double  getValorTotal();
      double getDesconto();
}


