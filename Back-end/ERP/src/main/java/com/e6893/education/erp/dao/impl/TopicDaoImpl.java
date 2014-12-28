package com.e6893.education.erp.dao.impl;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.stereotype.Repository;

import com.e6893.education.erp.dao.TopicDao;
import com.e6893.education.erp.dbFactory.DatabaseNeo4j;
import com.e6893.education.erp.entity.Topic;

@Repository
public class TopicDaoImpl implements TopicDao {


	@Override
	public Topic createTopic(Topic topic) {
		
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		
        ExecutionEngine engine = new ExecutionEngine( db );

		try ( Transaction tx = db.beginTx(); )
        {
            engine.execute("merge (tp: Topic {topicName:'" + topic.getTopicName() + "'})"
            		+ " return tp");
            tx.success();   
            return topic;
        }
		catch (Exception e) {
			return null;
		}
		finally {
			db.shutdown();
		}
	}
	

}
