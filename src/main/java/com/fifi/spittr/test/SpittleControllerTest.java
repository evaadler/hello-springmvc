package com.fifi.spittr.test;

import com.fifi.spittr.Spittle;
import com.fifi.spittr.data.SpittleRepository;
import com.fifi.spittr.web.SpittleController;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author fifi
 */
public class SpittleControllerTest {

    /* 针对spittles的GET请求 */
    @Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> expectedSittles = createSpittleList(20);


        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSittles);

        SpittleController controller = null;//new SpittleController(mockRepository);

        // Mock Spring mvc
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc.perform(get("/spittles"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"));
           //     .andExpect(model().attribute("spittleList", hasItems(expectedSittles.toArray())));
    }

    /* spittles分页 */
    @Test
    public void shouldShowPagedSpittles() throws Exception{
        List<Spittle> expectedSpittles = createSpittleList(50);
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        SpittleController controller = null; //new SpittleController(mockRepository);

        // 预期的max和count参数
        when(mockRepository.findSpittles(238900, 50))
                .thenReturn(expectedSpittles);
        MockMvc mockMvc = standaloneSetup(controller)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
                .build();

        mockMvc.perform(get("/spittles?max=238900&count=50"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList",
                        hasItems(expectedSpittles.toArray())));

    }

    private List<Spittle> createSpittleList(int count) {
        List<Spittle> spittles = new ArrayList<Spittle>();
        for (int i=0; i<count; i++){
            spittles.add(new Spittle("Spittle " + i, new Date()));
        }
        return spittles;
    }
}
