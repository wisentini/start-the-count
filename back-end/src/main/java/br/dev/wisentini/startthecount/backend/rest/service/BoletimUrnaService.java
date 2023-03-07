package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.*;
import br.dev.wisentini.startthecount.backend.rest.repository.BoletimUrnaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "boletim-urna")
public class BoletimUrnaService {

    private final QRCodeBoletimUrnaService qrCodeBoletimUrnaService;

    private final ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService;

    private final ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService;

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    private final BoletimUrnaUsuarioService boletimUrnaUsuarioService;

    private final BoletimUrnaRepository boletimUrnaRepository;

    private final BoletimUrnaMapper boletimUrnaMapper;

    private final CachingService cachingService;

    @Autowired
    public BoletimUrnaService(
        QRCodeBoletimUrnaService qrCodeBoletimUrnaService,
        ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService,
        ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService,
        ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService,
        @Lazy BoletimUrnaUsuarioService boletimUrnaUsuarioService,
        BoletimUrnaRepository boletimUrnaRepository,
        @Lazy BoletimUrnaMapper boletimUrnaMapper,
        CachingService cachingService
    ) {
        this.qrCodeBoletimUrnaService = qrCodeBoletimUrnaService;
        this.apuracaoVotosCandidaturaBoletimUrnaService = apuracaoVotosCandidaturaBoletimUrnaService;
        this.apuracaoVotosCargoBoletimUrnaService = apuracaoVotosCargoBoletimUrnaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
        this.boletimUrnaUsuarioService = boletimUrnaUsuarioService;
        this.boletimUrnaRepository = boletimUrnaRepository;
        this.boletimUrnaMapper = boletimUrnaMapper;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "T(java.lang.String).format('%d:%d:%s:%d', #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public BoletimUrna findById(BoletimUrnaIdDTO id) {
        return this.boletimUrnaRepository
            .findBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum boletim de urna identificado por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<BoletimUrna> findAll() {
        return this.boletimUrnaRepository.findAll();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public Set<Usuario> findUsuarios(BoletimUrnaIdDTO id) {
        return this
            .findById(id)
            .getUsuarios()
            .stream()
            .map(BoletimUrnaUsuario::getUsuario)
            .collect(Collectors.toSet());
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public Set<QRCodeBoletimUrna> findQRCodes(BoletimUrnaIdDTO id) {
        return this.findById(id).getQRCodes();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public Set<ApuracaoVotosCandidaturaBoletimUrna> findApuracoesVotosCandidaturas(BoletimUrnaIdDTO id) {
        return this.findById(id).getApuracoesVotosCandidaturas();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public Set<ApuracaoVotosCargoBoletimUrna> findApuracoesVotosCargos(BoletimUrnaIdDTO id) {
        return this.findById(id).getApuracoesVotosCargos();
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #root.methodName, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public Set<ApuracaoVotosPartidoBoletimUrna> findApuracoesVotosPartidos(BoletimUrnaIdDTO id) {
        return this.findById(id).getApuracoesVotosPartidos();
    }

    public BoletimUrna getIfExistsOrElseSave(BoletimUrna boletimUrna) {
        if (this.boletimUrnaRepository.existsBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(
            boletimUrna.getSecaoPleito().getSecao().getNumeroTSE(),
            boletimUrna.getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            boletimUrna.getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            boletimUrna.getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.boletimUrnaMapper.toBoletimUrnaIdDTO(boletimUrna));
        }

        this.cachingService.evictAllCaches();

        return this.boletimUrnaRepository.save(boletimUrna);
    }

    public void deleteById(BoletimUrnaIdDTO id) {
        id.validate();

        if (!this.boletimUrnaRepository.existsBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum boletim de urna identificado por %s.", id));
        }

        this.qrCodeBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosCargoBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByBoletimUrna(id);
        this.boletimUrnaUsuarioService.deleteByBoletimUrna(id);

        this.boletimUrnaRepository.deleteBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteBySecaoPleito(SecaoPleitoIdDTO id) {
        id.validate();

        this.deleteById(new BoletimUrnaIdDTO(id.getNumeroTSESecao(), id.getNumeroTSEZona(), id.getSiglaUF(), id.getCodigoTSEPleito()));

        this.cachingService.evictAllCaches();
    }

    private void deleteOrphanRelationships(BoletimUrna boletimUrna) {
        BoletimUrnaIdDTO id = this.boletimUrnaMapper.toBoletimUrnaIdDTO(boletimUrna);

        this.qrCodeBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosCargoBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByBoletimUrna(id);
        this.boletimUrnaUsuarioService.deleteByBoletimUrna(id);
    }

    public void deleteByFase(String nomefase) {
        this.boletimUrnaRepository
            .findByFaseNomeEqualsIgnoreCase(nomefase)
            .forEach(this::deleteOrphanRelationships);

        this.boletimUrnaRepository.deleteByFaseNomeEqualsIgnoreCase(nomefase);

        this.cachingService.evictAllCaches();
    }

    public void deleteByOrigemBoletimUrna(String nomeAbreviadoOrigemBoletimUrna) {
        this.boletimUrnaRepository
            .findByOrigemNomeAbreviadoEqualsIgnoreCase(nomeAbreviadoOrigemBoletimUrna)
            .forEach(this::deleteOrphanRelationships);

        this.boletimUrnaRepository.deleteByOrigemNomeAbreviadoEqualsIgnoreCase(nomeAbreviadoOrigemBoletimUrna);

        this.cachingService.evictAllCaches();
    }

    public void deleteByUrnaEletronica(Integer numeroSerieUrnaEletronica) {
        this.boletimUrnaRepository
            .findByUrnaEletronicaNumeroSerie(numeroSerieUrnaEletronica)
            .forEach(this::deleteOrphanRelationships);

        this.boletimUrnaRepository.deleteByUrnaEletronicaNumeroSerie(numeroSerieUrnaEletronica);

        this.cachingService.evictAllCaches();
    }
}
