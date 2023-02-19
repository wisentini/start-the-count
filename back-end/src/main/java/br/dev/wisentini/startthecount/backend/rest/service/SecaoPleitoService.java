package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoPleitoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoPleito;
import br.dev.wisentini.startthecount.backend.rest.repository.SecaoPleitoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecaoPleitoService {

    private final SecaoPleitoRepository secaoPleitoRepository;

    private final SecaoPleitoMapper secaoPleitoMapper;

    private final BoletimUrnaService boletimUrnaService;

    public SecaoPleito findById(SecaoPleitoIdDTO id) {
        return this.secaoPleitoRepository
            .findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de SecaoPleito identificada por %s.", id));
            });
    }

    public List<SecaoPleito> findAll() {
        return this.secaoPleitoRepository.findAll();
    }

    public List<Secao> findSecoesByPleito(Integer codigoTSEPleito) {
        return this.secaoPleitoRepository.findSecoesByPleito(codigoTSEPleito);
    }

    public List<Pleito> findPleitosBySecao(SecaoIdDTO id) {
        return this.secaoPleitoRepository.findPleitosBySecao(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF()
        );
    }

    public SecaoPleito getIfExistsOrElseSave(SecaoPleito secaoPleito) {
        if (this.secaoPleitoRepository.existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(
            secaoPleito.getSecao().getNumeroTSE(),
            secaoPleito.getSecao().getZona().getNumeroTSE(),
            secaoPleito.getSecao().getZona().getUF().getSigla(),
            secaoPleito.getPleito().getCodigoTSE()
        )) {
            return this.findById(this.secaoPleitoMapper.toSecaoPleitoIdDTO(secaoPleito));
        }

        return this.secaoPleitoRepository.save(secaoPleito);
    }

    public void deleteById(SecaoPleitoIdDTO id) {
        id.validate();

        if (!this.secaoPleitoRepository.existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma instância de SecaoPleito identificada por %s.", id));
        }

        this.boletimUrnaService.deleteBySecaoPleito(id);

        this.secaoPleitoRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );
    }

    public void deleteBySecao(SecaoIdDTO secaoId) {
        secaoId.validate();

        this.secaoPleitoRepository
            .findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(
                secaoId.getNumeroTSESecao(),
                secaoId.getNumeroTSEZona(),
                secaoId.getSiglaUF()
            ).forEach(secaoPleito -> this.boletimUrnaService.deleteBySecaoPleito(
                this.secaoPleitoMapper.toSecaoPleitoIdDTO(secaoPleito)
            ));

        this.secaoPleitoRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(
            secaoId.getNumeroTSESecao(),
            secaoId.getNumeroTSEZona(),
            secaoId.getSiglaUF()
        );
    }

    public void deleteByPleito(int codigoTSEPleito) {
        this.secaoPleitoRepository
            .findByPleitoCodigoTSE(codigoTSEPleito)
            .forEach(secaoPleito -> this.boletimUrnaService.deleteBySecaoPleito(
                this.secaoPleitoMapper.toSecaoPleitoIdDTO(secaoPleito)
            ));

        this.secaoPleitoRepository.deleteByPleitoCodigoTSE(codigoTSEPleito);
    }
}
