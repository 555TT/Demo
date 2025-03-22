package demo.es;

import demo.es.request.BookRequest;
import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author: wanghaoran1
 * @create: 2024-11-11
 */

@RestController
@Slf4j
public class Test1 {

    @Resource
    private JestClient jestClient;

    @GetMapping("/createIndex")
    public String test1() throws IOException {
        CreateIndex book = new CreateIndex.Builder("book").build();
        jestClient.execute(book);
        return "ok";
    }

    @PostMapping("search")
    public String search(@RequestBody BookRequest bookRequest) throws Exception {

    }
}
