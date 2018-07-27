package com.hereisalexius.ms;

import com.hereisalexius.ms.MemberServiceApplication;
import com.hereisalexius.ms.config.SecurityConfig;
import com.hereisalexius.ms.dao.ImagesDao;
import com.hereisalexius.ms.dao.ImagesDaoImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImagesDaoTests {

    private static final String TEST_IMAGE_URL = "https://static.highsnobiety.com/wp-content/uploads/2018/01/31094145/tommy-wiseau-the-room-3d-release-01-960x640.jpg";

    @SpyBean
    private ImagesDao imagesDao;

    private HashSet<String> savedIds;

    @Before
    public void init() {
        savedIds = new HashSet<>();
    }

    @After
    public void clear() {
        savedIds.forEach(s -> {
            imagesDao.delete(s);
        });
    }


    @Test
    public void saveAndLoadValid() throws Exception {
        String id = imagesDao.save(TEST_IMAGE_URL);
        savedIds.add(id);
        Assert.assertEquals("Id received!", true, id != null && !id.isEmpty());
        Assert.assertEquals("Image exists!", true, imagesDao.loadAsStream(id).getBody().exists());
    }

    @Test
    public void saveNullOrEmptyAndReadAsDefaultPic() throws Exception {
        String id = imagesDao.save("");
        savedIds.add(id);
        Assert.assertEquals("Id received!", true, id != null && !id.isEmpty());
        Assert.assertEquals("Image exists!", true, imagesDao.loadAsStream(id).getBody().exists());
    }

    public void saveWrongUrl() throws Exception {
        String id = imagesDao.save("not_valid_url");
        Assert.assertEquals("Id received!", true, id != null && !id.isEmpty());
        Assert.assertEquals("Image exists!", true, imagesDao.loadAsStream(id).getBody().exists());
    }

    @Test(expected = NullPointerException.class)
    public void deleteExistedThentryToRead() throws Exception {
        String id = imagesDao.save(TEST_IMAGE_URL);

        System.out.println(id);

        imagesDao.delete(id);
        imagesDao.loadAsStream(id);

    }

    //Expected nothing
    @Test
    public void deleteNotExisted() {
        imagesDao.delete("id");
    }

}
