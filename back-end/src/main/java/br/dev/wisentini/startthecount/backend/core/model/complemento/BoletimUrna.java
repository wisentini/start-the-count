package br.dev.wisentini.startthecount.backend.core.model.complemento;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoletimUrna {

    private String assinatura;

    private ProcessoEleitoral processoEleitoral;
}
