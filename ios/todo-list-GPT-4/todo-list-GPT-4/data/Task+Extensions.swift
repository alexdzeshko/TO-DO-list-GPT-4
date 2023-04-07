//
//  Task+Extensions.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import Foundation
import CoreData

extension Task {
    static func exampleTask1(context: NSManagedObjectContext) -> Task {
        let newTask = Task(context: context)
        newTask.title = "Sample Task"
        newTask.contentText = "This is an example of a task for SwiftUI preview."
        newTask.isCompleted = false
        newTask.id = UUID()
        return newTask
    }
}
