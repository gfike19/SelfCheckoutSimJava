package com.gfike.SelfCheckoutSim.daos;

import com.gfike.SelfCheckoutSim.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface OrdersDao extends CrudRepository<Item, Integer> {

}
