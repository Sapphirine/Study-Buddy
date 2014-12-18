package com.e6893.education.erp.dao.impl;

import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.springframework.stereotype.Repository;

import com.e6893.education.erp.dao.TopicDao;
import com.e6893.education.erp.dbFactory.DatabaseNeo4j;
import com.e6893.education.erp.entity.Topic;
import com.e6893.education.erp.entity.User;


@Repository
public class TopicDaoImpl implements TopicDao {


	@Override
	public Topic createTopic(Topic topic) {
		// TODO Auto-generated method stub
//		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
//		try ( Transaction tx = db.beginTx(); )
//        {
//            Node topicNode = db.createNode();
//            topicNode.setProperty( "topicName", topic.getTopicName() );
//            tx.success();
//            return topic;
//        }
//		catch (Exception e) {
//			return null;
//		}
//		finally {
//			db.shutdown();
//		}
		
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		
        ExecutionEngine engine = new ExecutionEngine( db );

		try ( Transaction tx = db.beginTx(); )
        {
            //engine.execute( "match (n) where n.userName = \"" + user.getUserName() + "\" set n.pwd = \"" + user.getPwd() + "\"");
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
	
//	public List<User> recommendUsers(Topic topic) {
//		
//GraphDatabaseService db = DatabaseNeo4j.getDatabase();
//		
//        ExecutionEngine engine = new ExecutionEngine( db );
//
//        List<User> buddies;
//		try ( Transaction tx = db.beginTx(); )
//        {
//            //engine.execute( "match (n) where n.userName = \"" + user.getUserName() + "\" set n.pwd = \"" + user.getPwd() + "\"");
//            engine.execute("merge (tp: Topic {topicName:'" + topic.getTopicName() + "'})"
//            		+ " return tp");
//            tx.success();
//            return topic;
//        }
//		catch (Exception e) {
//			return null;
//		}
//		finally {
//			db.shutdown();
//		}
//		
//		return null;
//	}

}
