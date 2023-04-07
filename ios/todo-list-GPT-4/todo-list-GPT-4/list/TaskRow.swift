//
//  TaskRow.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import Foundation
import SwiftUI

struct TaskRow: View {
    @StateObject var task: Task
    @StateObject var viewModel: TaskViewModel

    var body: some View {
        HStack {
            Button(action: toggleTaskCompletion) {
                Image(systemName: task.isCompleted ? "checkmark.square" : "square")
                    .foregroundColor(task.isCompleted ? .green : .gray)
            }
            .buttonStyle(BorderlessButtonStyle())

            VStack(alignment: .leading) {
                Text(task.title ?? "")
                    .font(.headline)
                    .strikethrough(task.isCompleted)
                Text(task.contentText ?? "")
                    .font(.subheadline)
                    .strikethrough(task.isCompleted)
            }
        }
    }

    private func toggleTaskCompletion() {
        viewModel.toggleTaskCompletion(task)
    }
}

//struct TaskRow_Previews: PreviewProvider {
//    static var previews: some View {
//        let context = TaskStore.preview.container.viewContext
//        TaskRow(task: Task.exampleTask1(context: context))
//            .environment(\.managedObjectContext, context)
//    }
//}
