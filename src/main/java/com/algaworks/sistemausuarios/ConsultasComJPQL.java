package com.algaworks.sistemausuarios;

import com.algaworks.sistemausuarios.dto.UsuarioDTO;
import com.algaworks.sistemausuarios.model.Dominio;
import com.algaworks.sistemausuarios.model.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ConsultasComJPQL {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Usuarios-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        primeirasConsultas(entityManager);
//        escolhendoRetorno(entityManager);
//        projetandoOResultado(entityManager);
//        passandoParametros(entityManager);
//        fazendoInnerJoin(entityManager);
//        usandoLeftOuterJoin(entityManager);
//        carregamentoEagerComJoinFetch(entityManager);
//        filtrandoRegistros(entityManager);
//        operadoresLogicos(entityManager);
//        ordenandoOResultado(entityManager);
        fazendoPaginacao(entityManager);

        entityManager.close();
        entityManagerFactory.close();
    }

    public static void primeirasConsultas(EntityManager entityManager) {
        System.out.println(">>> public static void primeiraConsulta(EntityManager entityManager)");

        String jpqlLista = "select u from Usuario u";
        Query query = entityManager.createQuery(jpqlLista);
        List<Usuario> lista = query.getResultList();
        lista.forEach(u -> System.out.println(
                String.format("%s, %s", u.getId(), u.getNome())));


        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpqlLista, Usuario.class);
        List<Usuario> listaTipada = typedQuery.getResultList();
        listaTipada.forEach(u -> System.out.println(
                String.format("%s, %s", u.getId(), u.getNome())));


        String jpqlSingular = "select u from Usuario u where u.id = 1";
        TypedQuery<Usuario> typedQuerySing = entityManager.createQuery(jpqlSingular, Usuario.class);
        Usuario usuario = typedQuerySing.getSingleResult();
        System.out.println(String.format("%s, %s", usuario.getId(), usuario.getNome()));
    }

    public static void escolhendoRetorno(EntityManager entityManager) {
        System.out.println(">>> public static void escolhendoRetorno(EntityManager entityManager)");

        String jpqlAtr = "select u.dominio from Usuario u where u.id = 1";
        TypedQuery<Dominio> typedQueryAtr = entityManager.createQuery(jpqlAtr, Dominio.class);
        Dominio dominio = typedQueryAtr.getSingleResult();
        System.out.println(String.format("%s, %s", dominio.getId(), dominio.getNome()));


        String jpqlPri = "select u.nome from Usuario u";
        TypedQuery<String> typedQueryPri = entityManager.createQuery(jpqlPri, String.class);
        List<String> listaPri = typedQueryPri.getResultList();
        listaPri.forEach(nome -> System.out.println(nome));
    }

    public static void projetandoOResultado(EntityManager entityManager) {
        System.out.println(">>> public static void projetandoOResultado(EntityManager entityManager)");

        String jpqlArr = "select id, login, nome from Usuario";
        TypedQuery<Object[]> typedQueryArr = entityManager.createQuery(jpqlArr, Object[].class);
        List<Object[]> listaArr = typedQueryArr.getResultList();
        listaArr.forEach(arr -> System.out.println(
                String.format("%s, %s, %s", arr)));


        String jpqlDto = "select new com.algaworks.sistemausuarios.dto.UsuarioDTO(id, login, nome) from Usuario";
        TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
        List<UsuarioDTO> listaDto = typedQueryDto.getResultList();
        listaDto.forEach(dto -> System.out.println(
                String.format("%s, %s", dto.getId(), dto.getNome())));
    }

    public static void passandoParametros(EntityManager entityManager) {
        System.out.println(">>> public static void passandoParametros(EntityManager entityManager)");

        String jpqlPos = "select u from Usuario u where u.id = ?1";
        TypedQuery<Usuario> typedQueryPos = entityManager
                .createQuery(jpqlPos, Usuario.class)
                .setParameter(1, 1);
        Usuario usuarioPos = typedQueryPos.getSingleResult();
        System.out.println(String.format("%s, %s", usuarioPos.getId(), usuarioPos.getNome()));


        String jpqlNam = "select u from Usuario u where u.login = :login";
        TypedQuery<Usuario> typedQueryNam = entityManager
                .createQuery(jpqlNam, Usuario.class)
                .setParameter("login", "cal");
        Usuario usuarioNam = typedQueryNam.getSingleResult();
        System.out.println(String.format("%s, %s", usuarioNam.getId(), usuarioNam.getNome()));
    }

    public static void fazendoInnerJoin(EntityManager entityManager) {
        System.out.println(">>> public static void fazendoInnerJoin(EntityManager entityManager)");

        String jpql = "select u from Usuario u inner join u.dominio d where d.id = :dominioId";
        TypedQuery<Usuario> typedQuery = entityManager
                .createQuery(jpql, Usuario.class)
                .setParameter("dominioId", 1);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(
                String.format("%s, %s", u.getId(), u.getNome())));
    }

    public static void usandoLeftOuterJoin(EntityManager entityManager) {
        System.out.println(">>> public static void usandoLeftOuterJoin(EntityManager entityManager)");

        String jpql = "select u from Usuario u left outer join u.configuracao c";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(
                String.format("%s, %s", u.getId(), u.getNome())));
    }

    public static void carregamentoEagerComJoinFetch(EntityManager entityManager) {
        System.out.println(">>> public static void carregamentoEagerComJoinFetch(EntityManager entityManager)");

        String jpql = "select u from Usuario u inner join fetch u.dominio inner join fetch u.configuracao";
        TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(d -> System.out.println(
                String.format("%s, %s", d.getId(), d.getNome())));
    }

    public static void filtrandoRegistros(EntityManager entityManager) {
        System.out.println(">>> public static void filtrandoRegistros(EntityManager entityManager)");

        String nomePesuisa = "Lightman";

        String jpqlLk = "select u from Usuario u where u.nome like concat('%', :nome, '%')";
        TypedQuery<Usuario> typedQueryLk = entityManager
                .createQuery(jpqlLk, Usuario.class)
                .setParameter("nome", nomePesuisa);
        List<Usuario> listaLk = typedQueryLk.getResultList();
        listaLk.forEach(d -> System.out.println(
                String.format("%s, %s", d.getId(), d.getNome())));


        String jpqlBt = "select u from Usuario u where u.ultimoAcesso between :ontem and :hoje";
        TypedQuery<Usuario> typedQueryBt = entityManager
                .createQuery(jpqlBt, Usuario.class)
                .setParameter("ontem", LocalDateTime.now().minusDays(1))
                .setParameter("hoje", LocalDateTime.now());
        List<Usuario> listaBt = typedQueryBt.getResultList();
        listaBt.forEach(d -> System.out.println(
                String.format("%s, %s", d.getId(), d.getNome())));
    }

    public static void operadoresLogicos(EntityManager entityManager) {
        System.out.println(">>> public static void operadoresLogicos(EntityManager entityManager)");

        String nomePesuisa = "Lightman";
        LocalDateTime ultimoAcessoPesquisa = LocalDateTime.now().minusDays(1);

        String jpql = "select u from Usuario u where u.nome like concat('%', :nome, '%') and u.ultimoAcesso > :ultimoAcesso";
        TypedQuery<Usuario> typedQuery = entityManager
                .createQuery(jpql, Usuario.class)
                .setParameter("nome", nomePesuisa)
                .setParameter("ultimoAcesso", ultimoAcessoPesquisa);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(d -> System.out.println(
                String.format("%s, %s", d.getId(), d.getNome())));
    }

    public static void ordenandoOResultado(EntityManager entityManager) {
        System.out.println(">>> public static void ordenandoOResultado(EntityManager entityManager)");

        String jpql = "select u from Usuario u order by u.nome";
        TypedQuery typedQuery = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(
                String.format("%s, %s", u.getId(), u.getNome())));
    }

    public static void fazendoPaginacao(EntityManager entityManager) {
        System.out.println(">>> public static void fazendoPaginacao(EntityManager entityManager)");

        String jpql = "select u from Usuario u order by u.nome";
        TypedQuery typedQuery = entityManager
                .createQuery(jpql, Usuario.class)
                .setFirstResult(4)
                .setMaxResults(2);
        List<Usuario> lista = typedQuery.getResultList();
        lista.forEach(u -> System.out.println(
                String.format("%s, %s", u.getId(), u.getNome())));
    }
}
