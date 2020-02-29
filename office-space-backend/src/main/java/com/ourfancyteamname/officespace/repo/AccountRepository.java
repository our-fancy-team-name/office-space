package com.ourfancyteamname.officespace.repo;

import com.ourfancyteamname.officespace.data.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
