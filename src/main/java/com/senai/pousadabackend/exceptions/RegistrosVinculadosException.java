package com.senai.pousadabackend.exceptions;

public class RegistrosVinculadosException extends RuntimeException {
    public RegistrosVinculadosException() {
      super("Não é possivel excluir pois existem registros vinculados");
    }

    public RegistrosVinculadosException(String mensagem) {
      super(mensagem);
    }
}
