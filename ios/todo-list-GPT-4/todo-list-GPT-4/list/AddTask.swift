//
//  AddTask.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import Foundation
import SwiftUI

struct AddTaskView: View {
    @Environment(\.presentationMode) private var presentationMode
    @State private var taskTitle: String = ""
    @State private var taskDescription: String = ""
    
    let addTask: (String, String) -> Void

    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("Task Details")) {
                    TextField("Title", text: $taskTitle)
                    TextField("Description", text: $taskDescription)
                }
            }
            .navigationBarTitle("Add Task")
            .navigationBarItems(leading: Button("Cancel") {
                presentationMode.wrappedValue.dismiss()
            }, trailing: Button("Save") {
                addTask(taskTitle, taskDescription)
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}

struct AddTaskView_Previews: PreviewProvider {
    static var previews: some View {
        AddTaskView(addTask: {_,_ in})
            .environment(\.managedObjectContext, TaskStore.preview.container.viewContext)
    }
}
