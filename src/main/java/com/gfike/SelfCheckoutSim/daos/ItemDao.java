package com.gfike.SelfCheckoutSim.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gfike.SelfCheckoutSim.models.Item;

@Transactional
@Repository
public interface ItemDao extends CrudRepository<Item, Integer> {
	
	Item findByName(String name);
	Item findById(int id);
	List <Item> findAll();
}
