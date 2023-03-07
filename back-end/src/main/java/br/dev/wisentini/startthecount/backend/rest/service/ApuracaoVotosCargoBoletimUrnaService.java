package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCargoBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.CargoEleicaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosCargoBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosCargoBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.repository.ApuracaoVotosCargoBoletimUrnaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "apuracao-votos-cargo-boletim-urna")
public class ApuracaoVotosCargoBoletimUrnaService {

    private final ApuracaoVotosCargoBoletimUrnaRepository apuracaoVotosCargoBoletimUrnaRepository;

    private final ApuracaoVotosCargoBoletimUrnaMapper apuracaoVotosCargoBoletimUrnaMapper;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%d:%d:%s:%d', #id.codigoTSECargo, #id.codigoTSEEleicao, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public ApuracaoVotosCargoBoletimUrna findById(ApuracaoVotosCargoBoletimUrnaIdDTO id) {
        return this.apuracaoVotosCargoBoletimUrnaRepository
            .findByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
                id.getCodigoTSECargo(),
                id.getCodigoTSEEleicao(),
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma apuração de votos de cargo por boletim de urna identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<ApuracaoVotosCargoBoletimUrna> findAll() {
        return this.apuracaoVotosCargoBoletimUrnaRepository.findAll();
    }

    public ApuracaoVotosCargoBoletimUrna getIfExistsOrElseSave(ApuracaoVotosCargoBoletimUrna apuracaoVotosCargoBoletimUrna) {
        if (this.apuracaoVotosCargoBoletimUrnaRepository.existsByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            apuracaoVotosCargoBoletimUrna.getCargoEleicao().getCargo().getCodigoTSE(),
            apuracaoVotosCargoBoletimUrna.getCargoEleicao().getEleicao().getCodigoTSE(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            apuracaoVotosCargoBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.apuracaoVotosCargoBoletimUrnaMapper.toApuracaoVotosCargoBoletimUrnaIdDTO(apuracaoVotosCargoBoletimUrna));
        }

        this.cachingService.evictAllCaches();

        return this.apuracaoVotosCargoBoletimUrnaRepository.save(apuracaoVotosCargoBoletimUrna);
    }

    public void deleteById(ApuracaoVotosCargoBoletimUrnaIdDTO id) {
        id.validate();

        if (!this.apuracaoVotosCargoBoletimUrnaRepository.existsByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma apuração de votos de cargo por boletim de urna identificada por %s.", id));
        }

        this.apuracaoVotosCargoBoletimUrnaRepository.deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByCargoEleicao(CargoEleicaoIdDTO cargoEleicaoId) {
        cargoEleicaoId.validate();

        this.apuracaoVotosCargoBoletimUrnaRepository.deleteByCargoEleicaoCargoCodigoTSEAndCargoEleicaoEleicaoCodigoTSE(
            cargoEleicaoId.getCodigoTSECargo(),
            cargoEleicaoId.getCodigoTSEEleicao()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByBoletimUrna(BoletimUrnaIdDTO boletimUrnaId) {
        boletimUrnaId.validate();

        this.apuracaoVotosCargoBoletimUrnaRepository.deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            boletimUrnaId.getNumeroTSESecao(),
            boletimUrnaId.getNumeroTSEZona(),
            boletimUrnaId.getSiglaUF(),
            boletimUrnaId.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }
}
