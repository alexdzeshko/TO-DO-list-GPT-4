//
//  TaskListView.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import Foundation
import SwiftUI

struct TaskList: View {
    @StateObject var viewModel: TaskViewModel
    
    var body: some View {
        ZStack {
            VStack {
                Text("TODOs")
                            .font(.largeTitle)
                            .fontWeight(.bold)
                List {
                    ForEach(viewModel.tasks) { task in
                        TaskRow(task: task, viewModel: viewModel)
                            .onTapGesture {
                                //viewModel.toggleTaskCompletion(task)
                            }
                    }
                }
                .sheet(isPresented: $viewModel.showAddTaskView) {
                    AddTaskView(addTask: viewModel.addTask)
                }
            }
            // Floating action button
            VStack {
                Spacer()
                HStack {
                    Spacer()
                    Button(action: {
                        self.viewModel.showAddTaskView = true
                    }) {
                        Image(systemName: "plus")
                            .font(.headline)
                            .foregroundColor(.white)
                    }
                    .padding()
                    .background(Color.blue)
                    .cornerRadius(40)
                    .padding(.trailing, 16)
                    .padding(.bottom, 16)
                }
            }
        }
    }
}

