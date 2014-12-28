package com.e6893.education.erp.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.stereotype.Repository;

import com.e6893.education.erp.dao.UserDao;
import com.e6893.education.erp.entity.Topic;
import com.e6893.education.erp.entity.User;
import com.e6893.education.erp.dbFactory.DatabaseNeo4j;

@Repository
public class UserDaoImpl implements UserDao {

	
	
	@Override
	public User getUserByUserName(String userName) {
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		ExecutionEngine engine = new ExecutionEngine( db );
		ExecutionResult result;
		User user = new User();
		try ( Transaction tx = db.beginTx(); )
        {
			user.setUserName(userName);
            result = engine.execute( "match (n) where n.userName = \"" + user.getUserName() + "\" return n");
            Iterator<Node> n_column = result.columnAs( "n" );
            
            if (!n_column.hasNext())
            	return null;
            Node node = n_column.next();
            user.setPwd(node.getProperty("pwd").toString());
             
            tx.success();   
            return user;
        }
		catch (Exception e) {
			return null;
		}
		finally {
			db.shutdown();
		}
	}

	@Override
	public User createUser(User user) {
		
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		
        ExecutionEngine engine = new ExecutionEngine( db );

		try ( Transaction tx = db.beginTx(); )
        {
            //engine.execute( "match (n) where n.userName = \"" + user.getUserName() + "\" set n.pwd = \"" + user.getPwd() + "\"");
            engine.execute("merge (me: User {userName:'" + user.getUserName() + "', pwd:'" + user.getPwd() + "'})"
            		+ " return me");
            tx.success();   
            return user;
        }
		catch (Exception e) {
			return null;
		}
		finally {
			db.shutdown();
		}
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		
        ExecutionEngine engine = new ExecutionEngine( db );

		try ( Transaction tx = db.beginTx(); )
        {
            engine.execute( "match (n) where n.userName = \"" + user.getUserName() + "\" set n.pwd = \"" + user.getPwd() + "\"");
            
            tx.success();   
            return user;
        }
		catch (Exception e) {
			return null;
		}
		finally {
			db.shutdown();
		}
	}

	public boolean verifyUser(User user) {
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		ExecutionEngine engine = new ExecutionEngine( db );
		ExecutionResult result;
		try ( Transaction tx = db.beginTx(); )
        {
            result = engine.execute( "match (n) where n.userName = \"" + user.getUserName() + "\" and n.pwd = \"" + user.getPwd() + "\" return n");
            Iterator<Node> n_column = result.columnAs( "n" );
            
            if (!n_column.hasNext())
            	return false;
             
            tx.success();   
            return true;
        }
		catch (Exception e) {
			return false;
		}
		finally {
			db.shutdown();
		}
	}
	
	public int addSearchedHistory(User user, Topic topic) {
		
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		ExecutionEngine engine = new ExecutionEngine( db );
		ExecutionResult result;
		try ( Transaction tx = db.beginTx(); )
        {
            result = engine.execute( "match (u:User {userName: '" + user.getUserName() + "'}) "
            		+ " merge (tp:Topic {topicName: '" + topic.getTopicName() 
            		+ "'})"
            		+ " merge (u)-[s:Searched]->(tp)"
            		+ " on match set s.searchCount = s.searchCount + 1 "
            		+ " on create set s.searchCount = 1"
            		+ " return s.searchCount as count");
            
            int searchCount = Integer.parseInt(result.columnAs("count").next().toString());
//            System.out.println(searchCount);
             
            tx.success();   
            return searchCount;
        }
		catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
		finally {
			db.shutdown();
		}
	}
	
	public void updateUserSimilarity() {
		
		// Periodically call this one
		
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		ExecutionEngine engine = new ExecutionEngine( db );
		try ( Transaction tx = db.beginTx(); )
        {
            engine.execute("MATCH (u1:User)-[h1:Searched]->(tp:Topic)<-[h2:Rated]-(u2:User) "
            		+ " WITH SUM(h1.searchCount * h2.searchCount) AS DotProduct, "
            		+ " SQRT(REDUCE(h1Dot = 0.0, a IN COLLECT(h1.searchCount) | h1Dot + a^2)) AS h1Length, "
            		+ " SQRT(REDUCE(h2Dot = 0.0, b IN COLLECT(h2.searchCount) | h2Dot + b^2)) AS h2Length, "
            		+ " u1, u2 "
            		+ " MERGE (u1)-[s:Similarity]-(u2) "
            		+ " SET s.similarity = DotProduct / (h1Length * h2Length)");
            
            tx.success();
        }
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			db.shutdown();
		}
	}
	
	public void updateTopicSimilarity() {
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		ExecutionEngine engine = new ExecutionEngine( db );
		try ( Transaction tx = db.beginTx(); )
        {
            engine.execute("MATCH (t1:Topic)<-[r1:Searched]-(u:User)-[r2:Searched]->(t2:Topic) "
            		+ "WITH SQRT(SUM((r1.searchCount - r2.searchCount)^2)) AS ecdif, t1, t2 "
            		+ "MERGE (t1)−[s:SIMILAR]−(t2) "
            		+ "SET s.similarity = 1 / (1 + ecdif)");
            engine.execute("MATCH (t1:Topic)<-[r1:Searched]-(u:User)-[r2:Searched]->(t2:Topic) WITH SQRT(SUM((r1.searchCount - r2.searchCount)^2)) AS ecdif, t1, t2 MERGE (t1)−[s:SIMILAR]−(t2) SET s.similarity = 1 / (1 + ecdif)");
            
            tx.success();
        }
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			db.shutdown();
		}
	}
	
	public List<User> recommendUser(User user,Topic topic) {
		// TODO Auto-generated method stub
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		
        ExecutionEngine engine = new ExecutionEngine( db );

        ExecutionResult result;
		try ( Transaction tx = db.beginTx(); )
        {
            result = engine.execute( "MATCH (u1:User)-[r1:Searched]->(t:Topic {topicName:\"" 
            		+ topic.getTopicName() + "\"})<-[r:Searched]-(A:User {userName:\""
            		+ user.getUserName() + "\"}) WITH u1, r1 ORDER BY r1.searchCount DESC RETURN u1 LIMIT 5");
            Iterator<Node> n_column = result.columnAs( "u1" );
            List<User> users =  new ArrayList<User>();
            for ( Node node : IteratorUtil.asIterable( n_column ) )
            {
                // note: we're grabbing the name property from the node,
                // not from the n.name in this case.
            	User tmpUser = new User();
            	tmpUser.setPwd(node.getProperty("pwd").toString());
            	tmpUser.setUserName(node.getProperty("userName").toString());
            	users.add(tmpUser);
            }
            tx.success();   
            return users;
        }
		catch (Exception e) {
			return null;
		}
		finally {
			db.shutdown();
		}
	}
	
	public List<Topic> recommendTopic(Topic topic) {
		// TODO Auto-generated method stub
		GraphDatabaseService db = DatabaseNeo4j.getDatabase();
		
        ExecutionEngine engine = new ExecutionEngine( db );

        ExecutionResult result;
        
        
		try ( Transaction tx = db.beginTx(); )
        {
			result = engine.execute("MATCH (T:Topic {topicName:\"" 
					+ topic.getTopicName() + "\"})-[s:SIMILAR]-(t1:Topic) "
					+ "WITH t1, s.similarity AS Similarity "
					+ "ORDER BY Similarity DESC "
					+ "RETURN t1 LIMIT 4");
            Iterator<Node> n_column = result.columnAs( "t1" );
            List<Topic> topics =  new ArrayList<Topic>();
            for ( Node node : IteratorUtil.asIterable( n_column ) )
            {
                // note: we're grabbing the name property from the node,
                // not from the n.name in this case.
            	Topic tmpTopic = new Topic();
            	tmpTopic.setTopicName(node.getProperty("topicName").toString());
            	topics.add(tmpTopic);
            }
            tx.success();   
            return topics;
        }
		catch (Exception e) {
			return null;
		}
		finally {
			db.shutdown();
		}
	}
}
