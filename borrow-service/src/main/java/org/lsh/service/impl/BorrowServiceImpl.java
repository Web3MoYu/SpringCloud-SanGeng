package org.lsh.service.impl;

import com.lsh.entity.Book;
import com.lsh.entity.Borrow;
import com.lsh.entity.User;
import jakarta.annotation.Resource;
import org.lsh.entity.UserBorrowDetail;
import org.lsh.mapper.BorrowMapper;
import org.lsh.service.BorrowService;
import org.lsh.service.client.BookClient;
import org.lsh.service.client.UserClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Resource
    BorrowMapper mapper;

    @Resource
    UserClient userClient;

    @Resource
    BookClient bookClient;

    @Override
    public UserBorrowDetail getUserBorrowDetailByUid(int uid) {
        List<Borrow> borrow = mapper.getBorrowsByUid(uid);
        //获取User信息
        User user = userClient.findUserById(uid);
        //获取每一本书的详细信息
        List<Book> bookList = borrow
                .stream()
                .map(b -> bookClient.findBookById(b.getBid()))
                .collect(Collectors.toList());
        return new UserBorrowDetail(user, bookList);
    }
}