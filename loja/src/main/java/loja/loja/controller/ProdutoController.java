package loja.loja.controller;

import loja.loja.entity.*;
import loja.loja.repository.*;
import loja.loja.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // ✅ CORRETO
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService service;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // MÉTODO NOVO ATUALIZADO (Mantenha este)
    @PostMapping("/upload")
    public Produto cadastrar(
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("descricao") String descricao,
            @RequestParam("imagens") MultipartFile[] files
    ) throws Exception {

        List<String> nomesArquivos = new ArrayList<>();
        String diretorioUpload = "uploads/";

        Path pasta = Paths.get(diretorioUpload);
        if (!Files.exists(pasta)) {
            Files.createDirectories(pasta);
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path caminho = pasta.resolve(nomeArquivo);
                Files.write(caminho, file.getBytes());
                nomesArquivos.add(nomeArquivo);
            }
        }

        Produto produto = new Produto();
        produto.setProduto(nome);
        produto.setValor(preco);
        produto.setDescricao(descricao);

        if (!nomesArquivos.isEmpty()) {
            // A primeira imagem do array vira a capa
            produto.setImagem(nomesArquivos.get(0));
            // A lista completa vai para a galeria (tabela auxiliar)
            produto.setImagens(nomesArquivos);
        }

        return produtoRepository.save(produto);
    }

    @GetMapping
    public List<Produto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @PostMapping("/carrinho/adicionar/{produtoId}")
    public void adicionar(@PathVariable Long produtoId, Authentication auth) {
        String email = auth.getName();
        Usuario usuario = usuarioRepository.findByEmail(email);
        Carrinho carrinho = usuario.getCarrinho();
        Produto produto = produtoRepository.findById(produtoId).orElseThrow();

        ItemCarrinho item = new ItemCarrinho();
        item.setProduto(produto);
        item.setQuantidade(1);
        item.setCarrinho(carrinho);

        carrinho.getItens().add(item);
        carrinhoRepository.save(carrinho);
    }
}