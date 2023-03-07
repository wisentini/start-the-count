package br.dev.wisentini.startthecount.backend.rest.service;

import br.dev.wisentini.startthecount.backend.core.model.*;
import br.dev.wisentini.startthecount.backend.core.model.complemento.CandidatoId;
import br.dev.wisentini.startthecount.backend.core.service.BoletimUrnaBuilder;
import br.dev.wisentini.startthecount.backend.rest.dto.build.BoletimUrnaBuildDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaIdDTO;
import br.dev.wisentini.startthecount.backend.rest.dto.id.BoletimUrnaUsuarioIdDTO;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeJaExisteException;
import br.dev.wisentini.startthecount.backend.rest.exception.EntidadeNaoEncontradaException;
import br.dev.wisentini.startthecount.backend.rest.model.AgregacaoSecao;
import br.dev.wisentini.startthecount.backend.rest.model.BoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.Candidatura;
import br.dev.wisentini.startthecount.backend.rest.model.Cargo;
import br.dev.wisentini.startthecount.backend.rest.model.CargoEleicao;
import br.dev.wisentini.startthecount.backend.rest.model.Eleicao;
import br.dev.wisentini.startthecount.backend.rest.model.Fase;
import br.dev.wisentini.startthecount.backend.rest.model.LocalVotacao;
import br.dev.wisentini.startthecount.backend.rest.model.Municipio;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.OrigemConfiguracaoProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.model.Partido;
import br.dev.wisentini.startthecount.backend.rest.model.Pleito;
import br.dev.wisentini.startthecount.backend.rest.model.ProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.model.QRCodeBoletimUrna;
import br.dev.wisentini.startthecount.backend.rest.model.Secao;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoPleito;
import br.dev.wisentini.startthecount.backend.rest.model.SecaoProcessoEleitoral;
import br.dev.wisentini.startthecount.backend.rest.model.TipoCargo;
import br.dev.wisentini.startthecount.backend.rest.model.UF;
import br.dev.wisentini.startthecount.backend.rest.model.UrnaEletronica;
import br.dev.wisentini.startthecount.backend.rest.model.Zona;
import br.dev.wisentini.startthecount.backend.rest.model.*;
import br.dev.wisentini.startthecount.backend.rest.repository.BoletimUrnaUsuarioRepository;
import br.dev.wisentini.startthecount.backend.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@CacheConfig(cacheNames = "boletim-urna-usuario")
public class BoletimUrnaUsuarioService {

    private final UFService ufService;

    private final UrnaEletronicaService urnaEletronicaService;

    private final MunicipioService municipioService;

    private final ZonaService zonaService;

    private final SecaoService secaoService;

    private final LocalVotacaoService localVotacaoService;

    private final OrigemConfiguracaoProcessoEleitoralService origemConfiguracaoProcessoEleitoralService;

    private final ProcessoEleitoralService processoEleitoralService;

    private final PleitoService pleitoService;

    private final EleicaoService eleicaoService;

    private final SecaoProcessoEleitoralService secaoProcessoEleitoralService;

    private final SecaoPleitoService secaoPleitoService;

    private final TipoCargoService tipoCargoService;

    private final CargoService cargoService;

    private final CargoEleicaoService cargoEleicaoService;

    private final PartidoService partidoService;

    private final CandidatoService candidatoService;

    private final CandidaturaService candidaturaService;

    private final FaseService faseService;

    private final OrigemBoletimUrnaService origemBoletimUrnaService;

    private final QRCodeBoletimUrnaService qrCodeBoletimUrnaService;

    private final ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService;

    private final ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService;

    private final ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService;

    private final BoletimUrnaService boletimUrnaService;

    private final BoletimUrnaUsuarioRepository boletimUrnaUsuarioRepository;

    private final AgregacaoSecaoService agregacaoSecaoService;

    private final CachingService cachingService;

    @Autowired
    public BoletimUrnaUsuarioService(
        UFService ufService,
        UrnaEletronicaService urnaEletronicaService,
        MunicipioService municipioService,
        ZonaService zonaService,
        SecaoService secaoService,
        LocalVotacaoService localVotacaoService,
        OrigemConfiguracaoProcessoEleitoralService origemConfiguracaoProcessoEleitoralService,
        ProcessoEleitoralService processoEleitoralService,
        PleitoService pleitoService,
        EleicaoService eleicaoService,
        SecaoProcessoEleitoralService secaoProcessoEleitoralService,
        @Lazy SecaoPleitoService secaoPleitoService,
        TipoCargoService tipoCargoService,
        CargoService cargoService,
        CargoEleicaoService cargoEleicaoService,
        PartidoService partidoService,
        CandidatoService candidatoService,
        CandidaturaService candidaturaService,
        FaseService faseService,
        OrigemBoletimUrnaService origemBoletimUrnaService,
        QRCodeBoletimUrnaService qrCodeBoletimUrnaService,
        ApuracaoVotosCandidaturaBoletimUrnaService apuracaoVotosCandidaturaBoletimUrnaService,
        ApuracaoVotosCargoBoletimUrnaService apuracaoVotosCargoBoletimUrnaService,
        ApuracaoVotosPartidoBoletimUrnaService apuracaoVotosPartidoBoletimUrnaService,
        BoletimUrnaService boletimUrnaService,
        BoletimUrnaUsuarioRepository boletimUrnaUsuarioRepository,
        AgregacaoSecaoService agregacaoSecaoService,
        CachingService cachingService
    ) {
        this.ufService = ufService;
        this.urnaEletronicaService = urnaEletronicaService;
        this.municipioService = municipioService;
        this.zonaService = zonaService;
        this.secaoService = secaoService;
        this.localVotacaoService = localVotacaoService;
        this.origemConfiguracaoProcessoEleitoralService = origemConfiguracaoProcessoEleitoralService;
        this.processoEleitoralService = processoEleitoralService;
        this.pleitoService = pleitoService;
        this.eleicaoService = eleicaoService;
        this.secaoProcessoEleitoralService = secaoProcessoEleitoralService;
        this.secaoPleitoService = secaoPleitoService;
        this.tipoCargoService = tipoCargoService;
        this.cargoService = cargoService;
        this.cargoEleicaoService = cargoEleicaoService;
        this.partidoService = partidoService;
        this.candidatoService = candidatoService;
        this.candidaturaService = candidaturaService;
        this.faseService = faseService;
        this.origemBoletimUrnaService = origemBoletimUrnaService;
        this.qrCodeBoletimUrnaService = qrCodeBoletimUrnaService;
        this.apuracaoVotosCandidaturaBoletimUrnaService = apuracaoVotosCandidaturaBoletimUrnaService;
        this.apuracaoVotosCargoBoletimUrnaService = apuracaoVotosCargoBoletimUrnaService;
        this.apuracaoVotosPartidoBoletimUrnaService = apuracaoVotosPartidoBoletimUrnaService;
        this.boletimUrnaService = boletimUrnaService;
        this.boletimUrnaUsuarioRepository = boletimUrnaUsuarioRepository;
        this.agregacaoSecaoService = agregacaoSecaoService;
        this.cachingService = cachingService;
    }

    @Cacheable(key = "T(java.lang.String).format('%s:%d:%d:%s:%d', #id.username, #id.numeroTSESecao, #id.numeroTSEZona, #id.siglaUF, #id.codigoTSEPleito)")
    public BoletimUrnaUsuario findById(BoletimUrnaUsuarioIdDTO id) {
        return this.boletimUrnaUsuarioRepository
            .findByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
                id.getUsername(),
                id.getNumeroTSESecao(),
                id.getNumeroTSEZona(),
                id.getSiglaUF(),
                id.getCodigoTSEPleito()
            )
            .orElseThrow(() -> {
                throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre boletim de urna e usuário identificada por %s.", id));
            });
    }

    @Cacheable(key = "#root.methodName")
    public List<BoletimUrnaUsuario> findAll() {
        return this.boletimUrnaUsuarioRepository.findAll();
    }

    public BoletimUrnaUsuario saveIfNotExists(BoletimUrnaUsuario boletimUrnaUsuario) {
        if (this.boletimUrnaUsuarioRepository.existsByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            boletimUrnaUsuario.getUsuario().getUsername(),
            boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE(),
            boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
            boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
            boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getPleito().getCodigoTSE()
        )) {
            throw new EntidadeJaExisteException(
                String.format(
                    "O usuário \"%s\" já coletou um boletim de urna no %s na UF \"%s\", zona %d, seção %d.",
                    boletimUrnaUsuario.getUsuario().getUsername(),
                    boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getPleito().getNome(),
                    boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getSecao().getZona().getUF().getSigla(),
                    boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getSecao().getZona().getNumeroTSE(),
                    boletimUrnaUsuario.getBoletimUrna().getSecaoPleito().getSecao().getNumeroTSE()
                )
            );
        }

        this.cachingService.evictAllCaches();

        return this.boletimUrnaUsuarioRepository.save(boletimUrnaUsuario);
    }

    public void deleteById(BoletimUrnaUsuarioIdDTO id) {
        id.validate();

        if (!this.boletimUrnaUsuarioRepository.existsByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getUsername(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        )) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada nenhuma relação entre boletim de urna e usuário identificada por %s.", id));
        }

        this.boletimUrnaUsuarioRepository.deleteByUsuarioUsernameEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getUsername(),
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByBoletimUrna(BoletimUrnaIdDTO id) {
        id.validate();

        this.boletimUrnaUsuarioRepository.deleteByBoletimUrnaSecaoPleitoSecaoNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaNumeroTSEAndBoletimUrnaSecaoPleitoSecaoZonaUfSiglaEqualsIgnoreCaseAndBoletimUrnaSecaoPleitoPleitoCodigoTSE(
            id.getNumeroTSESecao(),
            id.getNumeroTSEZona(),
            id.getSiglaUF(),
            id.getCodigoTSEPleito()
        );

        this.cachingService.evictAllCaches();
    }

    public void deleteByUsuario(String username) {
        this.boletimUrnaUsuarioRepository.deleteByUsuarioUsernameEqualsIgnoreCase(username);

        this.cachingService.evictAllCaches();
    }

    public void build(BoletimUrnaBuildDTO boletimUrnaBuildDTO, Usuario usuario) {
        boletimUrnaBuildDTO.validate();

        br.dev.wisentini.startthecount.backend.core.model.BoletimUrna boletimUrnaQRCode = BoletimUrnaBuilder.build(boletimUrnaBuildDTO.getPayloads());

        br.dev.wisentini.startthecount.backend.core.model.complemento.BoletimUrna complementoBoletimUrna = boletimUrnaQRCode.getDadosComplementares();

        UF uf = this.ufService.findBySigla(boletimUrnaQRCode.getConteudo().getUF().getSigla());

        UrnaEletronica urnaEletronica = this.urnaEletronicaService.getIfExistsOrElseSave(new UrnaEletronica(
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getNumeroSerie(),
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getCodigoIdentificacaoCarga(),
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getVersaoSoftware(),
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getDataAbertura(),
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getHorarioAbertura(),
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getDataFechamento(),
            boletimUrnaQRCode.getConteudo().getUrnaEletronica().getHorarioFechamento()
        ));

        Municipio municipio = this.municipioService.getIfExistsOrElseSave(new Municipio(
            complementoBoletimUrna.getProcessoEleitoral().getMunicipio().getCodigoTSE(),
            complementoBoletimUrna.getProcessoEleitoral().getMunicipio().getNome(),
            uf
        ));

        Zona zona = this.zonaService.getIfExistsOrElseSave(new Zona(
            boletimUrnaQRCode.getConteudo().getZona().getNumeroTSE(),
            uf
        ));

        Secao secao = this.secaoService.getIfExistsOrElseSave(new Secao(
            boletimUrnaQRCode.getConteudo().getSecao().getNumeroTSE(),
            zona
        ));

        LocalVotacao localVotacao = this.localVotacaoService.getIfExistsOrElseSave(new LocalVotacao(
            boletimUrnaQRCode.getConteudo().getLocalVotacao().getNumeroTSE(),
            zona,
            municipio
        ));

        OrigemConfiguracaoProcessoEleitoral origemConfiguracaoProcessoEleitoral = this.origemConfiguracaoProcessoEleitoralService.getIfExistsOrElseSave(new OrigemConfiguracaoProcessoEleitoral(
            boletimUrnaQRCode.getConteudo().getProcessoEleitoral().getOrigemConfiguracao().getNome(),
            boletimUrnaQRCode.getConteudo().getProcessoEleitoral().getOrigemConfiguracao().getNomeAbreviado()
        ));

        ProcessoEleitoral processoEleitoral = this.processoEleitoralService.getIfExistsOrElseSave(new ProcessoEleitoral(
            complementoBoletimUrna.getProcessoEleitoral().getCodigoTSE(),
            complementoBoletimUrna.getProcessoEleitoral().getNome(),
            origemConfiguracaoProcessoEleitoral
        ));

        Pleito pleito = this.pleitoService.getIfExistsOrElseSave(new Pleito(
            complementoBoletimUrna.getProcessoEleitoral().getPleito().getCodigoTSE(),
            complementoBoletimUrna.getProcessoEleitoral().getPleito().getNome().toLowerCase(),
            boletimUrnaQRCode.getConteudo().getPleito().getTurno().getNumero(),
            StringUtil.toLocalDate(complementoBoletimUrna.getProcessoEleitoral().getPleito().getData(), "dd/MM/yyyy"),
            processoEleitoral
        ));

        SecaoPleito secaoPleito = this.secaoPleitoService.getIfExistsOrElseSave(new SecaoPleito(
            secao,
            pleito
        ));

        for (br.dev.wisentini.startthecount.backend.core.model.AgregacaoSecao agregacaoSecao : boletimUrnaQRCode.getConteudo().getSecoesAgregadas()) {
            Secao secaoAgregada = this.secaoService.getIfExistsOrElseSave(new Secao(
                agregacaoSecao.getSecaoAgregada().getNumeroTSE(),
                zona
            ));

            this.agregacaoSecaoService.getIfExistsOrElseSave(new AgregacaoSecao(
                secao,
                secaoAgregada,
                processoEleitoral
            ));
        }

        Map<EleicaoId, Eleicao> eleicoes = new HashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.complemento.Eleicao complementoEleicao : complementoBoletimUrna.getProcessoEleitoral().getEleicoes().values()) {
            br.dev.wisentini.startthecount.backend.core.model.Eleicao eleicao = boletimUrnaQRCode
                .getConteudo()
                .getEleicoes()
                .get(new EleicaoId(complementoEleicao.getCodigoTSE()));

            eleicoes.put(
                new EleicaoId(complementoEleicao.getCodigoTSE()),
                this.eleicaoService.getIfExistsOrElseSave(new Eleicao(
                    complementoEleicao.getCodigoTSE(),
                    complementoEleicao.getNome(),
                    pleito.getAno(),
                    Objects.isNull(eleicao) ? null : eleicao.getQuantidadeVotosCargosMajoritarios(),
                    Objects.isNull(eleicao) ? null : eleicao.getQuantidadeVotosCargosProporcionais(),
                    pleito
                ))
            );
        }

        this.secaoProcessoEleitoralService.getIfExistsOrElseSave(new SecaoProcessoEleitoral(
            secao,
            processoEleitoral,
            localVotacao
        ));

        Map<Integer, TipoCargo> tiposCargo = new HashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.Cargo cargo : boletimUrnaQRCode.getConteudo().getCargos().values()) {
            tiposCargo.put(
                cargo.getTipo().getCodigoTSE(),
                this.tipoCargoService.getIfExistsOrElseSave(new TipoCargo(
                    cargo.getTipo().getCodigoTSE(),
                    cargo.getTipo().getNome()
                ))
            );
        }

        Map<CargoId, Cargo> cargos = new HashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.Cargo cargo : boletimUrnaQRCode.getConteudo().getCargos().values()) {
            br.dev.wisentini.startthecount.backend.core.model.complemento.Cargo complementoCargo = complementoBoletimUrna
                .getProcessoEleitoral()
                .getCargos()
                .get(new CargoId(cargo.getCodigoTSE()));

            cargos.put(
                new CargoId(cargo.getCodigoTSE()),
                this.cargoService.getIfExistsOrElseSave(new Cargo(
                    cargo.getCodigoTSE(),
                    complementoCargo.getNomeNeutro(),
                    complementoCargo.getNomeAbreviado(),
                    tiposCargo.get(cargo.getTipo().getCodigoTSE())
                ))
            );
        }

        Map<CargoEleicaoId, CargoEleicao> cargosEleicoes = new HashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.CargoEleicao cargoEleicao : boletimUrnaQRCode.getConteudo().getCargosEleicoes().values()) {
            cargosEleicoes.put(
                new CargoEleicaoId(cargoEleicao.getCargo().getCodigoTSE(), cargoEleicao.getEleicao().getCodigoTSE()),
                this.cargoEleicaoService.getIfExistsOrElseSave(new CargoEleicao(
                    cargos.get(new CargoId(cargoEleicao.getCargo().getCodigoTSE())),
                    eleicoes.get(new EleicaoId(cargoEleicao.getEleicao().getCodigoTSE()))
                ))
            );
        }

        Map<PartidoId, Partido> partidos = new HashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.Partido partido : boletimUrnaQRCode.getConteudo().getPartidos().values()) {
            br.dev.wisentini.startthecount.backend.core.model.complemento.Partido complementoPartido = complementoBoletimUrna
                .getProcessoEleitoral()
                .getPartidos()
                .get(new PartidoId(partido.getNumeroTSE()));

            partidos.put(
                new PartidoId(complementoPartido.getNumeroTSE()),
                this.partidoService.getIfExistsOrElseSave(new Partido(
                    complementoPartido.getNumeroTSE(),
                    complementoPartido.getNome(),
                    complementoPartido.getSigla()
                ))
            );
        }

        Map<CandidatoId, Candidato> candidatos = new LinkedHashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.Candidatura candidatura : boletimUrnaQRCode.getConteudo().getCandidaturas().values()) {
            br.dev.wisentini.startthecount.backend.core.model.complemento.Candidato complementoCandidato = complementoBoletimUrna
                .getProcessoEleitoral()
                .getCandidatosTitulares().get(
                    new CandidatoId(
                        candidatura.getNumeroTSECandidato(),
                        candidatura.getCargo().getCodigoTSE()
                    )
                );

            candidatos.put(
                new CandidatoId(
                    candidatura.getNumeroTSECandidato(),
                    candidatura.getCargo().getCodigoTSE()
                ),
                this.candidatoService.getIfExistsOrElseSave(new Candidato(
                    candidatura.getNumeroTSECandidato(),
                    complementoCandidato.getCodigoTSE(),
                    complementoCandidato.getNome()
                ))
            );
        }

        Map<CandidaturaId, Candidatura> candidaturas = new HashMap<>();

        for (br.dev.wisentini.startthecount.backend.core.model.Candidatura candidatura : boletimUrnaQRCode.getConteudo().getCandidaturas().values()) {
            candidaturas.put(
                new CandidaturaId(
                    candidatura.getNumeroTSECandidato(),
                    candidatura.getCargo().getCodigoTSE(),
                    candidatura.getEleicao().getCodigoTSE()
                ),
                this.candidaturaService.getIfExistsOrElseSave(new Candidatura(
                    candidatos.get(new CandidatoId(
                        candidatura.getNumeroTSECandidato(),
                        candidatura.getCargo().getCodigoTSE()
                    )),
                    cargosEleicoes.get(new CargoEleicaoId(
                        candidatura.getCargo().getCodigoTSE(),
                        candidatura.getEleicao().getCodigoTSE()
                    )),
                    partidos.get(new PartidoId(candidatura.getPartido().getNumeroTSE()))
                ))
            );
        }

        Fase fase = this.faseService.getIfExistsOrElseSave(new Fase(
            boletimUrnaQRCode.getConteudo().getFase().getCodigoTSE(),
            boletimUrnaQRCode.getConteudo().getFase().getNome()
        ));

        OrigemBoletimUrna origemBoletimUrna = this.origemBoletimUrnaService.getIfExistsOrElseSave(new OrigemBoletimUrna(
            boletimUrnaQRCode.getConteudo().getOrigem().getNome(),
            boletimUrnaQRCode.getConteudo().getOrigem().getNomeAbreviado()
        ));

        EmissaoBoletimUrna emissaoBoletimUrna = boletimUrnaQRCode.getConteudo().getEmissaoBoletimUrna();

        BoletimUrna boletimUrna = boletimUrnaService.getIfExistsOrElseSave(new BoletimUrna(
            secaoPleito,
            fase,
            origemBoletimUrna,
            urnaEletronica,
            boletimUrnaQRCode.getAssinatura(),
            boletimUrnaQRCode.getQuantidadeTotalQRCodes(),
            Objects.isNull(emissaoBoletimUrna) ? null : emissaoBoletimUrna.getData(),
            Objects.isNull(emissaoBoletimUrna) ? null : emissaoBoletimUrna.getHorario()
        ));

        BoletimUrnaUsuario boletimUrnaUsuario = this.saveIfNotExists(new BoletimUrnaUsuario(
            boletimUrna,
            usuario
        ));

        for (br.dev.wisentini.startthecount.backend.core.model.QRCodeBoletimUrna qrCodeBoletimUrna : boletimUrnaQRCode.getQRCodes()) {
            this.qrCodeBoletimUrnaService.getIfExistsOrElseSave(new QRCodeBoletimUrna(
                boletimUrna,
                qrCodeBoletimUrna.getPayloadCabecalho(),
                qrCodeBoletimUrna.getPayloadConteudo(),
                qrCodeBoletimUrna.getHash(),
                qrCodeBoletimUrna.getCabecalho().getMarcaInicioDados().getIndiceQRCode(),
                qrCodeBoletimUrna.getCabecalho().getVersaoFormatoRepresentacao().getNumeroCiclosEleitoraisDesdeImplementacao(),
                qrCodeBoletimUrna.getCabecalho().getVersaoFormatoRepresentacao().getNumeroRevisoesFormatoCiclo(),
                qrCodeBoletimUrna.getCabecalho().getVersaoChaveAssinatura()
            ));
        }

        for (ApuracaoVotosCandidatura apuracaoVotosCandidatura : boletimUrnaQRCode.getConteudo().getApuracoesVotosCandidaturas().values()) {
            this.apuracaoVotosCandidaturaBoletimUrnaService.getIfExistsOrElseSave(new ApuracaoVotosCandidaturaBoletimUrna(
                candidaturas.get(new CandidaturaId(
                    apuracaoVotosCandidatura.getCandidatura().getNumeroTSECandidato(),
                    apuracaoVotosCandidatura.getCandidatura().getCargo().getCodigoTSE(),
                    apuracaoVotosCandidatura.getCandidatura().getEleicao().getCodigoTSE()
                )),
                boletimUrna,
                apuracaoVotosCandidatura.getTotalVotosApurados()
            ));
        }

        for (ApuracaoVotosCargo apuracaoVotosCargo : boletimUrnaQRCode.getConteudo().getApuracoesVotosCargos().values()) {
            this.apuracaoVotosCargoBoletimUrnaService.getIfExistsOrElseSave(new ApuracaoVotosCargoBoletimUrna(
                cargosEleicoes.get(new CargoEleicaoId(
                    apuracaoVotosCargo.getCargo().getCodigoTSE(),
                    apuracaoVotosCargo.getEleicao().getCodigoTSE()
                )),
                boletimUrna,
                apuracaoVotosCargo.getQuantidadeEleitoresAptos(),
                apuracaoVotosCargo.getQuantidadeComparecimentoCargoSemCandidatos(),
                apuracaoVotosCargo.getQuantidadeVotosNominais(),
                apuracaoVotosCargo.getQuantidadeVotosDeLegenda(),
                apuracaoVotosCargo.getQuantidadeVotosEmBranco(),
                apuracaoVotosCargo.getQuantidadeVotosNulos(),
                apuracaoVotosCargo.getTotalVotosApurados()
            ));
        }

        for (ApuracaoVotosPartido apuracaoVotosPartido : boletimUrnaQRCode.getConteudo().getApuracoesVotosPartidos().values()) {
            this.apuracaoVotosPartidoBoletimUrnaService.getIfExistsOrElseSave(new ApuracaoVotosPartidoBoletimUrna(
                partidos.get(new PartidoId(apuracaoVotosPartido.getPartido().getNumeroTSE())),
                boletimUrna,
                cargosEleicoes.get(new CargoEleicaoId(
                    apuracaoVotosPartido.getCargo().getCodigoTSE(),
                    apuracaoVotosPartido.getEleicao().getCodigoTSE()
                )),
                apuracaoVotosPartido.getQuantidadeVotosDeLegenda(),
                apuracaoVotosPartido.getTotalVotosApurados()
            ));
        }
    }
}
