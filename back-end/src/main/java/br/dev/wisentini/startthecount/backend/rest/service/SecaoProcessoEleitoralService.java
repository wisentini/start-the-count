package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.LocalVotacaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoProcessoEleitoralIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoProcessoEleitoralMapper;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.repository.SecaoProcessoEleitoralRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "secao-processo-eleitoral")
public class SecaoProcessoEleitoralService {

    private final SecaoProcessoEleitoralRepository secaoProcessoEleitoralRepository;

    private final SecaoProcessoEleitoralMapper secaoProcessoEleitoralMapper;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%s:%d', #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEProcessoEleitoral)")
    public SecaoProcessoEleitoral findById(SecaoProcessoEleitoralIdDTO id) {
        return this.secaoProcessoEleitoralRepository
            .findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEProcessoEleitoral()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre seção e processo eleitoral identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<SecaoProcessoEleitoral> findAll() {
        return this.secaoProcessoEleitoralRepository.findAll();
    }

    public SecaoProcessoEleitoral getIfExistsOrElseSave(SecaoProcessoEleitoral secaoProcessoEleitoral) {
        if (this.secaoProcessoEleitoralRepository.existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            secaoProcessoEleitoral.getSecao().getNumeroTSE(),
            secaoProcessoEleitoral.getSecao().getZona().getNumeroTSE(),
            secaoProcessoEleitoral.getSecao().getZona().getUF().getSigla(),
            secaoProcessoEleitoral.getProcessoEleitoral().getCodigoTSE()
        )) {
            return this.findById(this.secaoProcessoEleitoralMapper.toSecaoProcessoEleitoralIdDTO(secaoProcessoEleitoral));
        }

        this.cachingService.evictAllCaches();

        return this.secaoProcessoEleitoralRepository.save(secaoProcessoEleitoral);
    }

    public void deleteById(SecaoProcessoEleitoralIdDTO id) {
        id.validate();

        if (!this.secaoProcessoEleitoralRepository.existsBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEProcessoEleitoral()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre seção e processo eleitoral identificada por %s.", id));
        }

        this.secaoProcessoEleitoralRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndProcessoEleitoralCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEProcessoEleitoral()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteBySecao(SecaoIdDTO secaoId) {
        secaoId.validate();

        this.secaoProcessoEleitoralRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(
            secaoId.getNumeroTSESecao(),
            secaoId.getNumeroTSEZona(),
            secaoId.getSiglaUF()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByProcessoEleitoral(int codigoTSEProcessoEleitoral) {
        this.secaoProcessoEleitoralRepository.deleteByProcessoEleitoralCodigoTSE(codigoTSEProcessoEleitoral);

        this.cachingService.evictAllCaches();
    }

    public void deleteByLocalVotacao(LocalVotacaoIdDTO localVotacaoId) {
        localVotacaoId.validate();

        this.secaoProcessoEleitoralRepository.deleteByLocalVotacaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCase(
            localVotacaoId.getNumeroTSELocalVotacao(),
            localVotacaoId.getNumeroTSEZona(),
            localVotacaoId.getSiglaUF()
        );

        this.cachingService.evictAllCaches();
    }
}
