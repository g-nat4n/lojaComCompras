package loja.loja.controller;

import loja.loja.entity.Produto;
import loja.loja.repository.ProdutoRepository;
import loja.loja.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;


    @Autowired
    private ProdutoService service;

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

        return repository.save(produto);
    }
}
