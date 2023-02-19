package br.dev.wisentini.startthecount.backend.core.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CargoEleicao {

    private Cargo cargo;

    private Eleicao eleicao;

    @Override
    public int hashCode() {
        return Objects.hash(this.cargo.hashCode(), this.eleicao.hashCode());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;

        if (Objects.isNull(object)) return false;

        if (this.getClass() != object.getClass()) return false;

        CargoEleicao cargoEleicao = (CargoEleicao) object;

        return
            Objects.equals(this.cargo.hashCode(), cargoEleicao.cargo.hashCode()) &&
            Objects.equals(this.eleicao.hashCode(), cargoEleicao.eleicao.hashCode());
    }
}
