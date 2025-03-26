# 🚀 Faster
기업과 기업 간의 대규모 물류를 배송, 관리하는 대형 물류 기업 서비스

### 🧚🏻‍♀️Project Goal 
- 물류 도메인을 기반으로 주문, 상품, 배송, 허브, 메시지 등의 마이크로서비스 아키텍처(MSA) 기반 백엔드 시스템을 구축합니다.
- 도메인 주도 설계(DDD) 를 적용하여 비즈니스 로직을 분리하고, 유연한 확장성과 유지보수성을 고려한 구조를 설계합니다.
- 데이터 정합성과 처리 효율성을 균형 있게 유지하며, MSA의 복잡성을 극복하는 실전 경험을 목표로 합니다.
- AI를 활용하여 최적의 발송 시한을 예측하고, 슬랙을 통해 허브 담당자에게 실시간 알림을 제공합니다.

> ### 🧑🏻‍🚀 Document | [wiki link](https://github.com/team-faster/faster/wiki)
> - [Instroduction:소개](https://github.com/team-faster/faster/wiki/Instroduction:%EC%86%8C%EA%B0%9C)
> - [Skiils:기술](https://github.com/team-faster/faster/wiki/Skiils:%EA%B8%B0%EC%88%A0)
> - Design Artifacts:설계산출물
>   - [api](https://github.com/team-faster/faster/wiki/api)
>   - [erd](https://github.com/team-faster/faster/wiki/erd)
>   - [architecture](https://github.com/team-faster/faster/wiki/architecture)
> - convention
>   - [commit message convention](https://github.com/team-faster/faster/wiki/commit-message-convention)
>   - [git flow](https://github.com/team-faster/faster/wiki/git-flow)
>   - [package structure](https://github.com/team-faster/faster/wiki/DDD-%EA%B3%84%EC%B8%B5-%EA%B5%AC%EC%A1%B0)
> - global concerns
>   - [AOP를 활용한 권한 체크](https://github.com/team-faster/faster/wiki/AOP%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EA%B6%8C%ED%95%9C%EC%B2%B4%ED%81%AC)
>   - [Auditing](https://github.com/team-faster/faster/wiki/Auditing)
>   - [공통 예외 처리](https://github.com/team-faster/faster/wiki/%EA%B3%B5%ED%86%B5-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC)
>   - [유저 정보 파싱](https://github.com/team-faster/faster/wiki/%EC%9C%A0%EC%A0%80-%EC%A0%95%EB%B3%B4-%ED%8C%8C%EC%8B%B1)
> - Troubleshooting:트러블슈팅
>   - [데이터 정렬을 통한 데드락 회피](https://github.com/team-faster/faster/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%A0%95%EB%A0%AC%EC%9D%84-%ED%86%B5%ED%95%9C-%EB%8D%B0%EB%93%9C%EB%9D%BD-%ED%9A%8C%ED%94%BC)
>   - [배송 담당자 추가 및 배정에 대한 동시성 제어](https://github.com/team-faster/faster/wiki/%EB%B0%B0%EC%86%A1-%EB%8B%B4%EB%8B%B9%EC%9E%90-%EC%B6%94%EA%B0%80-%EB%B0%8F-%EB%B0%B0%EC%A0%95%EC%97%90-%EB%8C%80%ED%95%9C-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%A0%9C%EC%96%B4)



## 🧑🏻‍🚀 Team Member Introduction & Retrospective
| 멤버                                 | 프로필                                                                                        | 역할         | 소감                                                                                                                                                                                                                                                                                                                |
|------------------------------------|--------------------------------------------------------------------------------------------|------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [전진우](https://github.com/hp5234)   | <img src="https://avatars.githubusercontent.com/u/62225840?v=4" width=100px alt="_"/>      | Leader. 배송 | 항상 베스트 케이스만 정답이라고 생각해왔었는데 심플하게 설계한 뒤 문제지점을 탐지, 개선할 수 있었던 값진 경험을 햇던것 같습니다. 실력 좋은 팀원들에게 좋은 소스, 스타일을 배울 수 있었던것 또한 값진 소득이었던것 같습니다.                                                                                                                                                                                    |
| [박지은](https://github.com/je-pa)    | <img src="https://avatars.githubusercontent.com/u/76720692?s=96&v=4" width=100px alt="_"/> | 허브         | 짧은 개발 기간동안 우여곡절이 있었지만, 그만큼 더 할 수 있었던 일이 생겨서 좋았습니다. 항상 팀프로젝트는 의사소통의 능력 향상에 도움이 된다고 생각됩니다.이전에 락 관련 개념을 이론위주로 공부를 해보았었는데, 이번에 적용할 일이 많아서 좋았습니다. 배송 담당자 배정, 시퀀스 관리 등에 락 개념을 도입하여 좋은 험이었습니다. 또한 이제껏 해왔던 알고리즘 공부도 적용할 수 있는 허브 이동경로의 경로 찾기 구현도 재미있었습니다. 단순 코딩 테스트 문제를 풀 뿐 아니라 DB에 있는 값들을 직접 꺼내와서 알고리즘을 작성했던 경험이 흥미로웠습니다. |
| [황하온](https://github.com/HanaHww2) | <img src="https://avatars.githubusercontent.com/u/62924471?v=4" width=130px alt="_"/>      | 주문         | 스프링 클라우드 서비스를 활용해 MSA 아키텍처를 구성해보고, DDD 아키텍쳐를 활용한 4계층 레이어를 구현해 볼 수 있는 값진 경험을 할 수 있었습니다. 또한, 새롭게 접해 본 모니터링 툴(zipkin, grafana) 과 테스트 툴(k6)를 활용해보면서 시스템의 부하를 측정해보는 경험과 시스템의 문제점을 분석하고 더 나은 방향으로 어떻게 개선해 볼 수 있을지 고민해 볼 수 있는 시간을 가질 수 있었습니다. 짧은 시간이었기에 아쉬움도 많이 남았지만, 나중에라도 리팩토링을 통해서 개선해볼 수 있는 시간을 가지면 좋을 것 같습니다.       |
| [임대일](https://github.com/LimdaeIl) | <img src="https://avatars.githubusercontent.com/u/131642334?v=4" width=130px alt="_"/>     | 회원         | 이번 프로젝트에서 처음 배우고 경험하는 것들이 정말 많았습니다. 다양한 문제에 부딪혔지만 팀원들의 적극적인 지원 덕분에 성공적으로 해결할 수 있었습니다. 팀원 모두가 늦은 시간까지 소통하며 각 담당 업무에서 작은 부분도 세밀하게 검토해주신 덕분에 더욱 구체적인 코드를 작성하고 다양한 관점에서 고민할 수 있는 소중한 경험을 했습니다. 더 나은 서비스를 구현하기 위해 다방면으로 열심히 하는 모습을 보면서 개발자로서의 성장에 큰 영향을 많이 받았습니다.                                                      |
