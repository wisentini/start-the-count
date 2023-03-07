package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.SecaoPleitoMapper;
import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoPleito;
import br.dev.wisentini.startthecount.backend.rest.repository.SecaoPleitoRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "secao-pleito")
public class SecaoPleitoService {

    private final SecaoPleitoRepository secaoPleitoRepository;

    private final SecaoPleitoMapper secaoPleitoMapper;

    private final BoletimUrnaService boletimUrnaService;

    private final CachingService cachingService;

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%s:%d', #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public SecaoPleito findById(SecaoPleitoIdDTO id) {
        return this.secaoPleitoRepository
            .findBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre seção e pleito identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<SecaoPleito> findAll() {
        return this.secaoPleitoRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public Set<BoletimUrna> findBoletinsUrna(SecaoPleitoIdDTO id) {
        return this.findById(id).getBoletinsUrna();
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

        this.cachingService.evictAllCaches();

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
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre seção e pleito identificada por %s.", id));
        }

        this.boletimUrnaService.deleteBySecaoPleito(id);

        this.secaoPleitoRepository.deleteBySecaoNumeroTSEAndSecaoZonaNumeroTSEAndSecaoZonaUfSiglaEqualsIgnoreCaseAndPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
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

        this.cachingService.evictAllCaches();
    }

    public void deleteByPleito(int codigoTSEPleito) {
        this.secaoPleitoRepository
            .findByPleitoCodigoTSE(codigoTSEPleito)
            .forEach(secaoPleito -> this.boletimUrnaService.deleteBySecaoPleito(
                this.secaoPleitoMapper.toSecaoPleitoIdDTO(secaoPleito)
            ));

        this.secaoPleitoRepository.deleteByPleitoCodigoTSE(codigoTSEPleito);

        this.cachingService.evictAllCaches();
    }
}
