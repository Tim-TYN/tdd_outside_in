# Using AI tools with test-driven development (TDD)

What is the performance of AI tools if you give them tests instead of descriptions of the desired function? 
In this project we developed a shopping cart system with the TDD school Outside-In. At the beginning we developed it without AI tools and then with the same tests with AI tools. 

You can find the exact chat history with ChatGPT-4o in the file [chatgpt_history_outside_in](chatgpt_history_outside_in.md) and with GitHub Copilot in the file [copilot_history_outside_in](copilot_history_outside_in.md).



mvn clean verify sonar:sonar \   -Dsonar.projectKey=Outside-In \   -Dsonar.projectName='Outside-In' \   -Dsonar.host.url=http://localhost:9000 \   -Dsonar.token=sqp_537b475524c9cc20e00801997f5f0182f736ba71