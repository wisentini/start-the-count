package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.SecaoPleitoIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.mapper.BoletimUrnaMapper;
import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.repository.BoletimUrnaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    public BoletimUrnaService(
        QRCodeBoletimUrnaService qrCodeBoletimUrnaService,
        ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService,
        ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService,
        ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService,
        @Lazy BoletimUrnaUsuarioService boletimUrnaUsuarioService,
        BoletimUrnaRepository boletimUrnaRepository,
        @Lazy BoletimUrnaMapper boletimUrnaMapper
    ) {
        this.qrCodeBoletimUrnaService = qrCodeBoletimUrnaService;
        this.apuracaoVotosCandidaturaBoletimUrnaService = apuracaoVotosCandidaturaBoletimUrnaService;
        this.apuracaoVotosCargoBoletimUrnaService = apuracaoVotosCargoBoletimUrnaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
        this.boletimUrnaUsuarioService = boletimUrnaUsuarioService;
        this.boletimUrnaRepository = boletimUrnaRepository;
        this.boletimUrnaMapper = boletimUrnaMapper;
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

    public List<BoletimUrna> findByUrnaEletronica(Integer numeroSerieUrnaEletronica) {
        return this.boletimUrnaRepository.findByUrnaEletronicaNumeroSerie(numeroSerieUrnaEletronica);
    }

    @CacheEvict(allEntries = true)
    public BoletimUrna getIfExistsOrElseSave(BoletimUrna boletimUrna) {
        if (this.boletimUrnaRepository.existsBySecaoPleitoSecaoNumeroTSEAndSecaoPleitoSecaoZonaNumeroTSEAndSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndSecaoPleitoPleitoCodigoTSE(
            boletimUrna.getSecaoPleito().getSecao().getNumeroTSE(),
            boletimUrna.getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            boletimUrna.getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            boletimUrna.getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            return this.findById(this.boletimUrnaMapper.toBoletimUrnaIdDTO(boletimUrna));
        }

        return this.boletimUrnaRepository.save(boletimUrna);
    }

    @CacheEvict(allEntries = true)
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
    }

    @CacheEvict(allEntries = true)
    public void deleteBySecaoPleito(SecaoPleitoIdDTO id) {
        id.validate();

        this.deleteById(new BoletimUrnaIdDTO(id.getNumeroTSESecao(), id.getNumeroTSEZona(), id.getSiglaUF(), id.getCodigoTSEPleito()));
    }

    private void deleteOrphanRelationships(BoletimUrna boletimUrna) {
        BoletimUrnaIdDTO id = this.boletimUrnaMapper.toBoletimUrnaIdDTO(boletimUrna);

        this.qrCodeBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosCandidaturaBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosCargoBoletimUrnaService.deleteByBoletimUrna(id);
        this.apuracaoVotosPartidoBoletimUrnaService.deleteByBoletimUrna(id);
        this.boletimUrnaUsuarioService.deleteByBoletimUrna(id);
    }

    @CacheEvict(allEntries = true)
    public void deleteByFase(String nomefase) {
        this.boletimUrnaRepository
            .findByFaseNomeEqualsIgnoreCase(nomefase)
            .forEach(this::deleteOrphanRelationships);

        this.boletimUrnaRepository.deleteByFaseNomeEqualsIgnoreCase(nomefase);
    }

    @CacheEvict(allEntries = true)
    public void deleteByOrigemBoletimUrna(String nomeAbreviadoOrigemBoletimUrna) {
        this.boletimUrnaRepository
            .findByOrigemNomeAbreviadoEqualsIgnoreCase(nomeAbreviadoOrigemBoletimUrna)
            .forEach(this::deleteOrphanRelationships);

        this.boletimUrnaRepository.deleteByOrigemNomeAbreviadoEqualsIgnoreCase(nomeAbreviadoOrigemBoletimUrna);
    }

    @CacheEvict(allEntries = true)
    public void deleteByUrnaEletronica(Integer numeroSerieUrnaEletronica) {
        this.boletimUrnaRepository
            .findByUrnaEletronicaNumeroSerie(numeroSerieUrnaEletronica)
            .forEach(this::deleteOrphanRelationships);

        this.boletimUrnaRepository.deleteByUrnaEletronicaNumeroSerie(numeroSerieUrnaEletronica);
    }
}
