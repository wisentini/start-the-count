package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.ApuracaoVotosCandidaturaBoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.CandidaturaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.ApuracaoVotosCandidaturaBoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.ApuracaoVotosCandidaturaBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.repository.ApuracaoVotosCandidaturaBoletimUrnaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "apuracao-votos-candidatura-boletim-urna")
public class ApuracaoVotosCandidaturaBoletimUrnaService {

    private final ApuracaoVotosCandidaturaBoletimUrnaRepository apuracaoVotosCandidaturaBoletimUrnaRepository;

    private final ApuracaoVotosCandidaturaBoletimUrnaMapper apuracaoVotosCandidaturaBoletimUrnaMapper;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%d:%d:%d:%s:%d', #id.numeroTSECandidato, #id.codigoTSECargo, #id.codigoTSEEleicao, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public ApuracaoVotosCandidaturaBoletimUrna findById(ApuracaoVotosCandidaturaBoletimUrnaIdDTO id) {
        return this.apuracaoVotosCandidaturaBoletimUrnaRepository
            .findByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
                id.getNumeroTSECandidato(),
                id.getCodigoTSECargo(),
                id.getCodigoTSEEleicao(),
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma apuração de votos de candidatura por boletim de urna identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<ApuracaoVotosCandidaturaBoletimUrna> findAll() {
        return this.apuracaoVotosCandidaturaBoletimUrnaRepository.findAll();
    }

    public ApuracaoVotosCandidaturaBoletimUrna getIfExistsOrElseSave(ApuracaoVotosCandidaturaBoletimUrna apuracaoVotosCandidaturaBoletimUrna) {
        if (this.apuracaoVotosCandidaturaBoletimUrnaRepository.existsByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            apuracaoVotosCandidaturaBoletimUrna.getCandidatura().getCandidato().getNumeroTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getCandidatura().getCargoEleicao().getCargo().getCodigoTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getCandidatura().getCargoEleicao().getEleicao().getCodigoTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            apuracaoVotosCandidaturaBoletimUrna.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.apuracaoVotosCandidaturaBoletimUrnaMapper.toApuracaoVotosCandidaturaBoletimUrnaIdDTO(apuracaoVotosCandidaturaBoletimUrna));
        }

        this.cachingService.evictAllCaches();

        return this.apuracaoVotosCandidaturaBoletimUrnaRepository.save(apuracaoVotosCandidaturaBoletimUrna);
    }

    public void deleteById(ApuracaoVotosCandidaturaBoletimUrnaIdDTO id) {
        id.validate();

        if (!this.apuracaoVotosCandidaturaBoletimUrnaRepository.existsByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSECandidato(),
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma apuração de votos de candidatura por boletim de urna identificada por %s.", id));
        }

        this.apuracaoVotosCandidaturaBoletimUrnaRepository.deleteByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSEAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSECandidato(),
            id.getCodigoTSECargo(),
            id.getCodigoTSEEleicao(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByCandidatura(CandidaturaIdDTO candidaturaId) {
        candidaturaId.validate();

        this.apuracaoVotosCandidaturaBoletimUrnaRepository.deleteByCandidaturaCandidatoNumeroTSEAndCandidaturaCargoEleicaoCargoCodigoTSEAndCandidaturaCargoEleicaoEleicaoCodigoTSE(
            candidaturaId.getNumeroTSECandidato(),
            candidaturaId.getCodigoTSECargo(),
            candidaturaId.getCodigoTSEEleicao()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByBoletimUrna(BoletimUrnaIdDTO boletimUrnaId) {
        boletimUrnaId.validate();

        this.apuracaoVotosCandidaturaBoletimUrnaRepository.deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            boletimUrnaId.getNumeroTSESecao(),
            boletimUrnaId.getNumeroTSEZona(),
            boletimUrnaId.getSiglaUF(),
            boletimUrnaId.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }
}
