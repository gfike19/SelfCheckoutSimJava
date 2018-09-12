package com.gfike.SelfCheckoutSim;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ItemDao extends CrudRepository<Item, Integer> {
	
	Item findByName(String name);
	Item findById(int id);
	List <Item> findAll();
}
