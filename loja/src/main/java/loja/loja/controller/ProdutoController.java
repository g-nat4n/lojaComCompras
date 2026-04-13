package loja.loja.controller;

import loja.loja.entity.*;
import loja.loja.repository.*;
import loja.loja.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // ✅ CORRETO
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
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


    @PostMapping
    public Produto cadastrar(@RequestBody Produto produto) {
        return service.salvar(produto);
    }

    @GetMapping
    public List<Produto> listar() {
        return service.listar();
    }

    @PostMapping("/upload")
    public Produto cadastrar(
            @RequestParam("nome") String nome,
            @RequestParam("preco") Double preco,
            @RequestParam("imagem") MultipartFile file
    ) throws Exception {

        String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path caminho = Paths.get("uploads/" + nomeArquivo);

        Files.createDirectories(caminho.getParent());
        Files.write(caminho, file.getBytes());

        Produto produto = new Produto();
        produto.setProduto(nome);
        produto.setValor(preco);
        produto.setImagem(nomeArquivo);

        return produtoRepository.save(produto);
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