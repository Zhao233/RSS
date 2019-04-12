package com.example.demo.service.user;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Cooker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CookerService {
    Page<Cooker> getAll(String search, Pageable pageable);

    Cooker getOne(long id);

    void addOne(Cooker cooker);

    void updateOne(Cooker cooker);

    void deleteOne(long id);
}
