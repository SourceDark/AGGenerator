package xr.tsa.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import xr.tsa.Model.Ad;
import xr.tsa.Model.AppCategories;
import xr.tsa.Model.Position;
import xr.tsa.Model.Test;
import xr.tsa.Model.Train;
import xr.tsa.Model.User;
import xr.tsa.Model.UserAppActions;
import xr.tsa.Model.UserInstalledApps;
import xr.tsa.support.AdRepository;
import xr.tsa.support.AppCategoriesRepository;
import xr.tsa.support.PositionRepository;
import xr.tsa.support.TestRepository;
import xr.tsa.support.TrainRepository;
import xr.tsa.support.UserAppActionsRepository;
import xr.tsa.support.UserInstalledAppsRepository;
import xr.tsa.support.UserRepository;

@RestController
@RequestMapping("")
public class AlgorithmController {

    @Autowired
    AdRepository adRepository;
    @Autowired
    AppCategoriesRepository appCategoriesRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    TestRepository testRepository;
    @Autowired
    TrainRepository trainRepository;
    @Autowired
    UserAppActionsRepository userAppActionsRepository;
    @Autowired
    UserInstalledAppsRepository userInstalledAppsRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/ad")
    public void csv2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/ad.csv"), "utf-8");
        lines.remove(0);
        List<Ad> ads = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            Ad ad = new Ad(values[0], values[1], values[2], values[3], values[4], values[5]);
            ads.add(ad);
        }
        adRepository.save(ads);
    }

    @GetMapping("/appCate")
    public void appCate2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/app_categories.csv"),
                "utf-8");
        lines.remove(0);
        List<AppCategories> datas = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            AppCategories data = new AppCategories(values[0], values[1]);
            datas.add(data);
        }
        appCategoriesRepository.save(datas);
    }

    @GetMapping("/position")
    public void positionCate2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/position.csv"), "utf-8");
        lines.remove(0);
        List<Position> datas = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            Position data = new Position(values[0], values[1], values[2]);
            datas.add(data);
        }
        positionRepository.save(datas);
    }

    @GetMapping("/test")
    public void testCate2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/test.csv"), "utf-8");
        lines.remove(0);
        List<Test> datas = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            Test data = new Test(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
            datas.add(data);
        }
        testRepository.save(datas);
    }

    @GetMapping("/train")
    public void trainCate2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/train-with-id.csv"),
                "utf-8");
        lines.remove(0);
        List<Train> datas = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            Train data = new Train(values[0], values[1], values[3], values[4], values[5], values[6], values[7],
                    values[8]);
            datas.add(data);
        }
        saveInBatch(Train.class, datas, trainRepository, 0, 30000);
    }

    @GetMapping("/user")
    public void user2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/user.csv"), "utf-8");
        lines.remove(0);
        List<User> datas = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            User data = new User(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
            datas.add(data);
        }
        saveInBatch(User.class, datas, userRepository, 0, 30000);
    }

    @GetMapping("/user-actions")
    public void userActions2db() throws IOException {
        List<String> lines = FileUtils.readLines(new File("/Users/xr/tmp/python-data/tsa/pre/user_app_actions.csv"),
                "utf-8");
        lines.remove(0);
        List<UserAppActions> datas = Lists.newArrayList();
        for (String line : lines) {
            String[] values = line.split(",");
            UserAppActions data = new UserAppActions(values[0], values[1], values[2]);
            datas.add(data);
        }
        saveInBatch(UserAppActions.class, datas, userAppActionsRepository, 0, 30000);
    }

    @GetMapping("/user-apps")
    public void userApps2db() throws IOException {
        File file = new File("/Users/xr/tmp/python-data/tsa/pre/user_installedapps.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        saveUserInstalledApps(reader, 30000);
        reader.close();
    }

    public void saveUserInstalledApps(BufferedReader reader, int step) throws IOException {
        List<UserInstalledApps> datas = Lists.newArrayList();
        String line;
        int lineNum = 0;
        while ((line = reader.readLine()) != null && lineNum < step) {
            String[] values = line.split(",");
            UserInstalledApps data = new UserInstalledApps(values[0], values[1]);
            datas.add(data);
            lineNum++;
        }
        userInstalledAppsRepository.save(datas);
        userInstalledAppsRepository.flush();
        if(line != null) {
            saveUserInstalledApps(reader, step);
        }
    }

    public <T> void saveInBatch(Class<T> clazz, List<T> datas, JpaRepository<T, Long> repository, int start, int step) {
        boolean stop = false;
        List<T> subDatas;
        if (start + step > datas.size()) {
            subDatas = datas.subList(start, datas.size());
            stop = true;
        } else {
            subDatas = datas.subList(start, start + step);
        }
        repository.save(subDatas);
        repository.flush();
        if (!stop) {
            saveInBatch(clazz, datas, repository, start + step, step);
        }
    }
    
    @GetMapping("/train-hour")
    public void trainHour2db() throws IOException {
        trainHour2db(trainRepository.findAll(new PageRequest(0, 30000)));
    }
    
    public void trainHour2db(Page<Train> trainPage) {
        for(Train train: trainPage.getContent()) {
            train.setClickHour((int)(train.getClickTime() / 100 % 100));
        }
        trainRepository.save(trainPage.getContent());
        if(trainPage.hasNext()) {
            trainHour2db(trainRepository.findAll(trainPage.nextPageable()));
        }
    }
    
    public static void main(String[] args) {
        Long t = 172134l;
        System.out.println((int)(t / 100 % 100));
    }

}
