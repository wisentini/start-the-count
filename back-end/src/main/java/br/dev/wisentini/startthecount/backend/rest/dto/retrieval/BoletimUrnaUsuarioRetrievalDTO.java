package br.dev.wisentini.startthecount.backend.rest.dto.retrieval;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"id", "usuario", "boletimUrna", "coletadoEm", "atualizadoEm"})
public class BoletimUrnaUsuarioRetrievalDTO {

    private Integer id;

    private UsuarioRetrievalDTO usuario;

    private BoletimUrnaRetrievalDTO boletimUrna;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime coletadoEm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime atualizadoEm;
}
