//
//  TaskViewModel.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import Foundation
import SwiftUI
import CoreData

class TaskViewModel: ObservableObject {
    @Published var tasks: [Task] = []
    @Published var showAddTaskView: Bool = false
    
    private let viewContext: NSManagedObjectContext

    init(viewContext: NSManagedObjectContext) {
        self.viewContext = viewContext
        fetchTasks()
    }
    
    func fetchTasks() {
        let fetchRequest: NSFetchRequest<Task> = Task.fetchRequest()
        fetchRequest.sortDescriptors = [NSSortDescriptor(keyPath: \Task.isCompleted, ascending: true)]

        do {
            tasks = try viewContext.fetch(fetchRequest)
        } catch {
            print("Failed to fetch tasks: \(error)")
        }
    }
    
    func addTask(title: String, description: String) {
        let newTask = Task(context: viewContext)
        newTask.id = UUID()
        newTask.title = title
        newTask.contentText = description
        newTask.isCompleted = false

        do {
            try viewContext.save()
            fetchTasks()
        } catch {
            print("Failed to save new task: \(error)")
        }
    }
    
    func toggleTaskCompletion(_ task: Task) {
        task.isCompleted.toggle()
        
        do {
            try viewContext.save()
            fetchTasks()
        } catch {
            print("Failed to update task: \(error)")
        }
    }
}
