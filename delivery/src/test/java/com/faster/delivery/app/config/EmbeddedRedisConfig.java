package com.faster.delivery.app.config;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

@Slf4j
@Profile("test")
@Configuration
public class EmbeddedRedisConfig {

  @Value("${spring.data.redis.port}")
  private int redisPort;

  private RedisServer redisServer;

  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://localhost:" + redisPort).setDatabase(0);

    return Redisson.create(config);
  }

  @PostConstruct
  public void redisServer() throws IOException {
    redisPort = isRedisRunning()? findAvailablePort() : redisPort;
    redisServer = new RedisServer(redisPort);
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }

  /**
   * Embedded Redis가 현재 실행중인지 확인
   */
  private boolean isRedisRunning() throws IOException {
    return isRunning(executeGrepProcessCommand(redisPort));
  }


  /**
   * 현재 PC/서버에서 사용가능한 포트 조회
   */
  public int findAvailablePort() throws IOException {

    for (int port = 10000; port <= 65535; port++) {
      Process process = executeGrepProcessCommand(port);
      if (!isRunning(process)) {
        return port;
      }
      log.info(">>>>>>>> Fail Port: "+port);
    }

    throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
  }

  /**
   * 해당 port를 사용중인 프로세스 확인하는 sh 실행
   */
  private Process executeGrepProcessCommand(int port) throws IOException {
    String os = System.getProperty("os.name").toLowerCase();
    String command;

    if (os.contains("win")) {
      // Windows용 명령어
      command = String.format("netstat -nao | find \"LISTENING\" | find \"%d\"", port);
      String[] cmd = {"cmd.exe", "/c", command};
      return Runtime.getRuntime().exec(cmd);
    } else {
      // Unix 계열용 명령어
      command = String.format("netstat -nat | grep LISTEN | grep %d", port);
      String[] shell = {"/bin/sh", "-c", command};
      return Runtime.getRuntime().exec(shell);
    }
  }

  /**
   * 해당 Process가 현재 실행중인지 확인
   */
  private boolean isRunning(Process process) {
    String line;
    StringBuilder pidInfo = new StringBuilder();

    try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

      while ((line = input.readLine()) != null) {
        pidInfo.append(line);
      }

    } catch (Exception e) {
    }

    return !StringUtils.isEmpty(pidInfo.toString());
  }
}
