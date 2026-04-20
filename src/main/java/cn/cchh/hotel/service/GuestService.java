package cn.cchh.hotel.service;

import cn.cchh.hotel.dto.GuestDTO;
import cn.cchh.hotel.entity.Guest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GuestService extends IService<Guest> {

    boolean checkIn(GuestDTO guestDTO);

    boolean checkOut(Long guestId);

    boolean updateGuest(GuestDTO guestDTO);

    boolean deleteGuest(Long id);

    Page<Guest> getGuestPage(Integer current, Integer size, String username,
                             String phone, Long roomId, Integer status);

    List<Guest> searchGuests(String username, String phone, Long roomId, Integer status);

    Guest getGuestById(Long id);

    List<Guest> getCurrentGuests();

    List<Guest> getGuestsByRoomId(Long roomId);
}
