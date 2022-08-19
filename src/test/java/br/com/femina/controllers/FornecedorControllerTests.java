package br.com.femina.controllers;

import br.com.femina.entities.Fornecedor;
import br.com.femina.services.FornecedorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    @Test
    public void verifyEmail() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        Fornecedor fornecedor = new Fornecedor("anna", "12345", "123450","123", "anna@gmail.com", "858622660","parana", "casa", "foz", "brasil");

        List<Fornecedor> fornecedorList = new ArrayList<>();
        fornecedorList.add(fornecedor);

        Page<Fornecedor> fornecedorPage = new PageImpl(fornecedorList);

        when(fornecedorService.findAll(pageable)).thenReturn(fornecedorPage);
        this.mockMvc.perform(get("/api/fornecedores")).andExpect(status().isOk());
        assertThat(fornecedorPage.getContent().get(0).getEmail(), equalTo("anna@gmail.com"));
    }

}
